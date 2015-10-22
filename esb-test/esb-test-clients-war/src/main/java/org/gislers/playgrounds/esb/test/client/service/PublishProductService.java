package org.gislers.playgrounds.esb.test.client.service;

import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.common.model.ProductInfo;

import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/21/15
 */
@Named
public class PublishProductService {

    private static final String PI_ENDPOINT = "http://localhost:8080/esb-gateway/api/product";
    private Logger logger = Logger.getLogger(PublishProductService.class.getSimpleName());
    private Random random = new Random();

    public void batchSend(int count) {
        for (int i = 0; i < count; i++) {
            GatewayResponse gatewayResponse = sendProductInfo("jim-sim", "4.0", generateProductInfo());
            if (!(gatewayResponse.getErrorItems().isEmpty())) {
                logger.warning(gatewayResponse.toString());
            }
        }
    }

    ProductInfo generateProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(random.nextLong());
        productInfo.setDescription(UUID.randomUUID().toString());
        productInfo.setName(UUID.randomUUID().toString());
        productInfo.setUnitPrice(new BigDecimal(random.nextDouble()));
        return productInfo;
    }

    GatewayResponse sendProductInfo(String envName, String messageVersion, ProductInfo productInfo) {
        return ClientBuilder.newClient()
                .target(PI_ENDPOINT)
                .request()
                .header("envName", envName)
                .header("messageVersion", messageVersion)
                .post(Entity.entity(productInfo, MediaType.APPLICATION_JSON_TYPE), GatewayResponse.class);
    }
}
