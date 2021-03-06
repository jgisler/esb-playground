package org.gislers.playgrounds.esb.gateway.resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.gislers.playgrounds.esb.common.http.ErrorItem;
import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;
import org.gislers.playgrounds.esb.gateway.ejb.publish.PublishProduct;
import org.gislers.playgrounds.esb.gateway.ejb.publish.dto.PublishProductDto;
import org.gislers.playgrounds.esb.gateway.ejb.publish.exception.PublishProductException;
import org.gislers.playgrounds.esb.gateway.service.ValidationService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by jim
 * Created on 9/27/15.
 */
@Stateless
@Path("/product")
public class ProductResource {

    @Inject
    private Logger logger;

    @Inject
    private ValidationService validationService;

    @EJB
    private PublishProduct productPublisher;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProduct( @HeaderParam(MessageConstants.TRANSACTION_ID)   String txId,
                                    @HeaderParam(MessageConstants.ENV_NAME)         String envName,
                                    @HeaderParam(MessageConstants.MESSAGE_VERSION)  String messageVersion,
                                    ProductInfo productInfo ) {

        PublishProductDto productDto = new PublishProductDto.Builder()
                .environmentName(envName)
                .messageVersion(messageVersion)
                .txId(txId)
                .productInfo(productInfo)
                .build();

        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setTxId( txId );
        gatewayResponse.setHttpStatus( HttpStatus.SC_ACCEPTED );

        List<ErrorItem> errorItems = validationService.validate(productDto);
        if( !(errorItems.isEmpty()) ) {
            gatewayResponse.setErrorItems(errorItems);
            gatewayResponse.setHttpStatus(HttpStatus.SC_BAD_REQUEST);
        }
        else {
            try {
                productPublisher.publishProduct(productDto);
            }
            catch(PublishProductException e) {
                gatewayResponse.getErrorItems().add(new ErrorItem(ExceptionUtils.getRootCauseMessage(e)));
                gatewayResponse.setHttpStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            }
        }

        return Response.status(gatewayResponse.getHttpStatus())
                .entity(gatewayResponse)
                .build();
    }
}
