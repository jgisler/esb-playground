package org.gislers.playgrounds.esb.gateway.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.esb.common.http.ErrorItem;
import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;
import org.gislers.playgrounds.esb.gateway.service.SerializationService;
import org.gislers.playgrounds.esb.gateway.service.ValidationService;
import org.gislers.playgrounds.esb.service.publish.PublishService;
import org.gislers.playgrounds.esb.service.publish.dto.ProductInfoDto;
import org.gislers.playgrounds.esb.service.publish.exception.PublishException;

import javax.ejb.EJB;
import javax.inject.Inject;
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
import java.util.logging.Logger;

/**
 * Created by jim
 * Created on 9/27/15.
 */
@Path("/product")
public class ProductResource {

    private static final Logger logger = Logger.getLogger( ProductResource.class.getSimpleName() );

    @EJB
    private PublishService publishService;

    @Inject
    private SerializationService serializationService;

    @Inject
    private ValidationService validationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProduct( @HeaderParam(MessageConstants.TRANSACTION_ID)   String txId,
                                    @HeaderParam(MessageConstants.ENV_NAME)         String envName,
                                    @HeaderParam(MessageConstants.MESSAGE_VERSION)  String messageVersion,
                                    ProductInfo productInfo ) {
        Response response;
        try {
            ProductInfoDto productDto = new ProductInfoDto.Builder()
                    .environmentName(envName)
                    .messageVersion(messageVersion)
                    .txId(txId)
                    .payload(serializationService.toJson(productInfo))
                    .build();

            List<String> errors = validationService.validate(productDto);
            if( !errors.isEmpty() ) {
                response = buildErrorResponse(txId, errors, Response.Status.BAD_REQUEST);
            }
            else {
                publishService.publish(productDto);
                response = buildSuccessResponse( productDto );
            }
        }
        catch( JsonProcessingException e ) {
            response = buildErrorResponse(txId, e, Response.Status.BAD_REQUEST);
        }
        catch( PublishException e ) {
            response = buildErrorResponse(txId, e, Response.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    Response buildSuccessResponse( ProductInfoDto productDto ) {
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setTxId(productDto.getTxId());
        return Response.accepted(gatewayResponse)
                .build();
    }

    Response buildErrorResponse(String txId, Throwable throwable, Response.Status status) {
        List<String> errors = new ArrayList<>();
        errors.add( ExceptionUtils.getRootCauseMessage(throwable) );
        return buildErrorResponse(txId, errors, status);
    }

    Response buildErrorResponse(String txId, List<String> errors, Response.Status status) {
        GatewayResponse response = new GatewayResponse();
        response.setTxId( txId );
        for( String error : errors ) {
            response.getErrorItems().add( new ErrorItem(UUID.randomUUID(), error) );
        }
        return Response.status(status)
                .entity( response )
                .build();
    }
}
