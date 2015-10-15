package org.gislers.playgrounds.esb.test.client.endpoint.resource;

import org.gislers.playgrounds.esb.test.client.endpoint.model.ClientEndpointResponse;
import org.gislers.playgrounds.esb.test.client.endpoint.model.ProductInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public abstract class AbstractResource {

    protected abstract String getClient();

    protected ClientEndpointResponse buildClientResponse( String txId, String service ) {
        ClientEndpointResponse response = new ClientEndpointResponse();
        response.setTxId( txId );
        response.setClient( getClient() );
        response.setService( service );
        return response;
    }

    @POST
    @Path("/product/v3")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveV3( @HeaderParam("ESB_TX_ID") String txId, ProductInfo productInfo ) {
        return Response.accepted()
                .entity(buildClientResponse(txId, "productV3"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @POST
    @Path("/product/v4")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveV4( @HeaderParam("ESB_TX_ID") String txId, ProductInfo productInfo ) {
        return Response.accepted()
                .entity(buildClientResponse(txId, "productV4"))
                .type( MediaType.APPLICATION_JSON_TYPE )
                .build();
    }
}
