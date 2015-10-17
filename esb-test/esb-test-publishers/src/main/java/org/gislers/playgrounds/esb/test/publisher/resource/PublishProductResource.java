package org.gislers.playgrounds.esb.test.publisher.resource;

import org.gislers.playgrounds.esb.test.publisher.model.ProductInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jim
 * Created on 10/17/15.
 */
@Path("/product")
public class PublishProductResource {

    private String ENDPOINT = "http://localhost:8080/"

    @POST
    @Path("/publish")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProduct(ProductInfo productInfo) {

    }
}
