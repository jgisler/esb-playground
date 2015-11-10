package org.gislers.playgrounds.esb.test.client.service;

import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Created by:   jgisle
 * Created date: 11/10/15
 */
public class PublishMessageTask implements Callable<GatewayResponse> {

    private static final String PI_ENDPOINT = "http://127.0.0.1:8080/esb-gateway/api/product";
    private static final Random random = new Random();

    public PublishMessageTask() {
    }

    @Override
    public GatewayResponse call() throws Exception {
        Response response = null;
        GatewayResponse gatewayResponse = null;
        try {
            response = ClientBuilder.newClient()
                    .target(PI_ENDPOINT)
                    .request()
                    .header(MessageConstants.ENV_NAME, "jim-sim")
                    .header(MessageConstants.MESSAGE_VERSION, getRandomMessageVersion())
                    .post(Entity.entity(generateProductInfo(), MediaType.APPLICATION_JSON_TYPE), Response.class);

            if (response != null) {
                gatewayResponse = response.readEntity(GatewayResponse.class);
            }
        }
        finally {
            if( response != null ) {
                response.close();
            }
        }
        return gatewayResponse;
    }

    private String getRandomMessageVersion() {
        if( random.nextInt()%2==0 ) {
            return "4.0";
        }
        return "2.0";
    }

    private ProductInfo generateProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(random.nextLong());
        productInfo.setDescription(UUID.randomUUID().toString());
        productInfo.setName(UUID.randomUUID().toString());
        productInfo.setUnitPrice(new BigDecimal(random.nextDouble()));
        return productInfo;
    }
}
