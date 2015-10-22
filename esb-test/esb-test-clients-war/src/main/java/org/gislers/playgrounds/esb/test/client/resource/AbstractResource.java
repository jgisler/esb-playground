package org.gislers.playgrounds.esb.test.client.resource;

import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;
import org.gislers.playgrounds.esb.test.client.model.ClientEndpointResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public abstract class AbstractResource {

    private static final Logger logger = Logger.getLogger(AbstractResource.class.getSimpleName());

    protected abstract String getClient();

    @POST
    @Path("/product/v3")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveV3(@HeaderParam(MessageConstants.TIMESTAMP) String timestamp,
                              @HeaderParam(MessageConstants.TRANSACTION_ID) String txId,
                              ProductInfo productInfo) {

        long txTime = System.currentTimeMillis() - Long.parseLong(timestamp);
        logger.info("tx time: " + txTime + "ms");

        ClientEndpointResponse clientEndpointResponse = new ClientEndpointResponse();
        clientEndpointResponse.setService("product/v3");
        clientEndpointResponse.setClient(getClient());
        clientEndpointResponse.setTxId(txId);
        clientEndpointResponse.setGatewayTimestamp(timestamp);

        return Response.accepted()
                .entity(clientEndpointResponse)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @POST
    @Path("/product/v4")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveV4(@HeaderParam(MessageConstants.TIMESTAMP) String timestamp,
                              @HeaderParam(MessageConstants.TRANSACTION_ID) String txId,
                              ProductInfo productInfo) {

        long txTime = System.currentTimeMillis() - Long.parseLong(timestamp);
        logger.info("tx time: " + txTime + "ms");

        ClientEndpointResponse clientEndpointResponse = new ClientEndpointResponse();
        clientEndpointResponse.setService("product/v4");
        clientEndpointResponse.setClient(getClient());
        clientEndpointResponse.setTxId(txId);
        clientEndpointResponse.setGatewayTimestamp(timestamp);

        return Response.accepted()
                .entity(clientEndpointResponse)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
