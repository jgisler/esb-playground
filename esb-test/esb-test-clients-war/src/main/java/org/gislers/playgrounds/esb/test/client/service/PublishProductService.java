package org.gislers.playgrounds.esb.test.client.service;

import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
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
@Singleton
public class PublishProductService {

    private static final String PI_ENDPOINT = "http://localhost:8080/esb-gateway/api/product";
    private Logger logger = Logger.getLogger(PublishProductService.class.getSimpleName());
    private Random random = new Random();

    @Inject
    private TrackingService trackingService;

    public String batchSend(int count) {
        String batchId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            String msgVersion = i%2==0 ? "4.0" : "2.0";
            GatewayResponse gatewayResponse = sendProductInfo( batchId, "jim-sim", msgVersion, generateProductInfo());
            if( gatewayResponse.getErrorItems().isEmpty() ) {
                trackingService.trackSend( batchId, gatewayResponse.getTxId() );
            }
            else {
                logger.warning(gatewayResponse.toString());
            }
        }
        return batchId;
    }

    ProductInfo generateProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(random.nextLong());
        productInfo.setDescription(UUID.randomUUID().toString());
        productInfo.setName(UUID.randomUUID().toString());
        productInfo.setUnitPrice(new BigDecimal(random.nextDouble()));
        return productInfo;
    }

    GatewayResponse sendProductInfo( String batchId, String envName, String messageVersion, ProductInfo productInfo) {
        return ClientBuilder.newClient()
                .target(PI_ENDPOINT)
                .request()
                .header(MessageConstants.BATCH_ID, batchId)
                .header(MessageConstants.ENV_NAME, envName)
                .header(MessageConstants.MESSAGE_VERSION, messageVersion)
                .post(Entity.entity(productInfo, MediaType.APPLICATION_JSON_TYPE), GatewayResponse.class);
    }
}
