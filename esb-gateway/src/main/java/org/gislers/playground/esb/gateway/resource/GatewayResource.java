package org.gislers.playground.esb.gateway.resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playground.esb.common.ErrorItem;
import org.gislers.playground.esb.common.GatewayResponse;
import org.gislers.playground.esb.common.Product;
import org.gislers.playground.esb.gateway.services.GatewayService;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by jim
 * Created on 9/27/15.
 */
@Path("/api")
public class GatewayResource {

    private GatewayService gatewayService;

    @Inject
    public void setGatewayService(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @POST
    @Path("/product")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendProduct(@HeaderParam("envName") String envName, @HeaderParam("messageVersion") String messageVersion, Product product) {

        GatewayResponse gatewayResponse = new GatewayResponse();
        try {
            UUID txId = gatewayService.sendProduct(envName, messageVersion, product);
            gatewayResponse.setTransactionId(txId);
            return Response.accepted(gatewayResponse)
                    .build();
        } catch (JMSException e) {
            ErrorItem errorItem = new ErrorItem(UUID.randomUUID(), ExceptionUtils.getRootCauseMessage(e));
            gatewayResponse.getErrorItems().add(errorItem);
            return Response.serverError()
                    .entity(gatewayResponse)
                    .build();
        }
    }
}
