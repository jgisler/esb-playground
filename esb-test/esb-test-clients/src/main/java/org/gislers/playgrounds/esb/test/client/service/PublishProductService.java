package org.gislers.playgrounds.esb.test.client.service;

import org.gislers.playgrounds.esb.test.client.model.GatewayResponse;
import org.gislers.playgrounds.esb.test.client.model.ProductInfo;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/21/15
 */
@Named
@Singleton
public class PublishProductService {

    private static final Logger logger = Logger.getLogger( PublishProductService.class.getSimpleName() );

    private static final String PI_ENDPOINT = "http://localhost:8080/esb-gateway/api/product";

    @Inject
    private TrackingService trackingService;

    private Random random;
    private ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    void init() {
        random = new Random();
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(40);
    }

    public Map<String, String> batchSend( int count ) {
        trackingService.reset();
        for( int i=0; i<count; i++ ) {
            threadPoolExecutor.execute(new Task(i));
        }

        while( threadPoolExecutor.getActiveCount() > 0 ) {
            try {
                logger.info( "Snoozing..." );
                Thread.sleep(5l);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return trackingService.getStats();
    }

    ProductInfo generateProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(random.nextLong());
        productInfo.setDescription(UUID.randomUUID().toString());
        productInfo.setName(UUID.randomUUID().toString());
        productInfo.setUnitPrice(new BigDecimal(random.nextDouble()));
        return productInfo;
    }

    GatewayResponse sendProductInfo( String envName, String messageVersion, ProductInfo productInfo ) {
        return ClientBuilder.newClient()
                .target(PI_ENDPOINT)
                .request()
                .header("envName", envName)
                .header("messageVersion", messageVersion)
                .post(Entity.entity(productInfo, MediaType.APPLICATION_JSON_TYPE), GatewayResponse.class);
    }

    class Task implements Runnable {

        private int index;

        public Task(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            ProductInfo productInfo = generateProductInfo();

            String messageVersion = "4.0";
            if( index%2 == 0 ) {
                messageVersion = "3.0";
            }

            GatewayResponse gatewayResponse = sendProductInfo("jim-sim", messageVersion, productInfo);
            trackingService.trackSend( gatewayResponse.getTxId() );
        }
    }
}
