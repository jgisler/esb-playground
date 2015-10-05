package org.gislers.playground.esb.gateway.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playground.esb.common.ErrorItem;
import org.gislers.playground.esb.common.GatewayResponse;
import org.gislers.playground.esb.common.Product;
import org.gislers.playground.esb.gateway.dto.ProductDto;
import org.gislers.playground.esb.gateway.services.GatewayService;
import org.gislers.playground.esb.gateway.services.SerializationService;
import org.gislers.playground.esb.gateway.services.ValidationService;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jim
 * Created on 9/27/15.
 */
@Path("/api")
public class GatewayResource {

    @Inject
    private ValidationService validationService;

    @Inject
    private GatewayService gatewayService;

    @Inject
    private SerializationService serializationService;

    @POST
    @Path("/product")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendProduct(@HeaderParam("envName") String envName, @HeaderParam("messageVersion") String messageVersion, Product product) {

        UUID txId = UUID.randomUUID();

        Response response;
        try {
            ProductDto productDto = buildProductDto( txId, envName, messageVersion, product );

            List<String> errors = validationService.validate(productDto);
            if( !errors.isEmpty() ) {
                response = buildErrorResponse( txId, errors, Response.Status.BAD_REQUEST );
            }
            else {
                gatewayService.sendProduct(productDto);
                response = buildSuccessResponse( productDto );
            }
        }
        catch( JsonProcessingException e ) {
            response = buildErrorResponse( txId, e, Response.Status.BAD_REQUEST );
        }
        catch( JMSException e ) {
            response = buildErrorResponse( txId, e, Response.Status.INTERNAL_SERVER_ERROR );
        }
        return response;
    }

    ProductDto buildProductDto( UUID txId, String envName, String messageVersion, Product product ) throws JsonProcessingException {
        String payload = serializationService.toJson(product);

        ProductDto productDto = new ProductDto();
        productDto.setTxId( txId.toString() );
        productDto.setEnvironmentName( envName );
        productDto.setMessageVersion( messageVersion );
        productDto.setPayload(payload);
        return productDto;
    }

    Response buildSuccessResponse( ProductDto productDto ) {
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setTxId(productDto.getTxId());
        return Response.accepted(gatewayResponse)
                .build();
    }

    Response buildErrorResponse( UUID txId, Throwable throwable, Response.Status status ) {
        List<String> errors = new ArrayList<>();
        errors.add( ExceptionUtils.getRootCauseMessage(throwable) );
        return buildErrorResponse(txId, errors, status);
    }

    Response buildErrorResponse( UUID txId, List<String> errors, Response.Status status ) {
        GatewayResponse response = new GatewayResponse();
        response.setTxId( txId.toString() );
        for( String error : errors ) {
            response.getErrorItems().add( new ErrorItem(UUID.randomUUID(), error) );
        }
        return Response.status(status)
                .entity( response )
                .build();
    }
}
