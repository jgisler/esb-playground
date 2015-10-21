package org.gislers.playgrounds.esb.test.client.resource;

import org.gislers.playgrounds.esb.test.client.service.PublishProductService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by:   jgisle
 * Created date: 10/19/15
 */
@Path("/publish")
public class PublishResource {

    @Inject
    private PublishProductService publishProductService;

    @GET
    @Path( "/productinfo/{count}" )
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProductInfo( @PathParam("count") int count ) {
        return Response.status(Response.Status.OK)
                .entity(publishProductService.batchSend(count))
                .build();
    }
}
