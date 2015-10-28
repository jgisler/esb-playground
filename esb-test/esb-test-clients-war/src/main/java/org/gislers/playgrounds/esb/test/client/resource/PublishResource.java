package org.gislers.playgrounds.esb.test.client.resource;

import org.gislers.playgrounds.esb.test.client.service.PublishProductService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/19/15
 */
@Path("/publish")
public class PublishResource {

    private static final Logger logger = Logger.getLogger(PublishResource.class.getSimpleName());

    @Inject
    private PublishProductService publishProductService;

    @GET
    @Path( "/productinfo/{count}" )
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProductInfo( @PathParam("count") int count ) {

        logger.info("Requesting " + count + " messages...");
        publishProductService.batchSend( count );
        return Response.status(Response.Status.OK).build();
    }
}
