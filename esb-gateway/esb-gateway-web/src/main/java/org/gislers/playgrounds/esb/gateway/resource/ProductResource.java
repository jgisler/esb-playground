package org.gislers.playgrounds.esb.gateway.resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.gislers.playgrounds.esb.common.http.ErrorItem;
import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;
import org.gislers.playgrounds.esb.gateway.service.ValidationService;
import org.gislers.playgrounds.esb.service.publish.PublishService;
import org.gislers.playgrounds.esb.service.publish.dto.ProductInfoDto;
import org.gislers.playgrounds.esb.service.publish.exception.PublishException;

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

    private static final Logger logger = Logger.getLogger( ProductResource.class.getSimpleName() );

    @EJB
    private PublishService publishService;

    @Inject
    private ValidationService validationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProduct( @HeaderParam(MessageConstants.TRANSACTION_ID)   String txId,
                                    @HeaderParam(MessageConstants.ENV_NAME)         String envName,
                                    @HeaderParam(MessageConstants.MESSAGE_VERSION)  String messageVersion,
                                    ProductInfo productInfo ) {

        ProductInfoDto productDto = new ProductInfoDto.Builder()
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
                publishService.publish(productDto);
            }
            catch (PublishException e) {
                gatewayResponse.getErrorItems().add(new ErrorItem(ExceptionUtils.getRootCauseMessage(e)));
                gatewayResponse.setHttpStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            }
        }

        return Response.status(gatewayResponse.getHttpStatus())
                .entity(gatewayResponse)
                .build();
    }
}
