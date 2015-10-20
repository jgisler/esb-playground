package org.gislers.playgrounds.esb.test.client.resource;

import org.gislers.playgrounds.esb.test.client.model.GatewayResponse;
import org.gislers.playgrounds.esb.test.client.model.ProductInfo;
import org.gislers.playgrounds.esb.test.client.service.TrackingService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/19/15
 */
@Path("/publish")
public class PublishResource {

    private static final Logger logger = Logger.getLogger( PublishResource.class.getSimpleName() );

    private static final String PI_ENDPOINT = "http://localhost:8080/esb-gateway/api/product";
    private static final Random random = new Random();

    @Inject
    private TrackingService trackingService;

    @GET
    @Path( "/productinfo/{count}" )
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProductInfo( @PathParam("count") int count ) {
        trackingService.reset();
        for( int i=0; i<count; i++ ) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(random.nextLong());
            productInfo.setDescription(UUID.randomUUID().toString());
            productInfo.setName(UUID.randomUUID().toString());
            productInfo.setUnitPrice(new BigDecimal(random.nextDouble()));

            String messageVersion = "4.0";
            if( i%2 == 0 ) {
                messageVersion = "3.0";
            }

            GatewayResponse gatewayResponse = sendProductInfo("jim-sim", messageVersion, productInfo);
            trackingService.trackSend( gatewayResponse.getTxId() );
        }

        return Response.status(Response.Status.OK)
                .entity(trackingService.getStats())
                .build();
    }

    GatewayResponse sendProductInfo( String envName, String messageVersion, ProductInfo productInfo ) {
        return ClientBuilder.newClient()
                .target(PI_ENDPOINT)
                .request()
                .header("envName", envName)
                .header("messageVersion", messageVersion)
                .header("timestamp", System.currentTimeMillis())
                .post(Entity.entity(productInfo, MediaType.APPLICATION_JSON_TYPE), GatewayResponse.class);
    }
}
