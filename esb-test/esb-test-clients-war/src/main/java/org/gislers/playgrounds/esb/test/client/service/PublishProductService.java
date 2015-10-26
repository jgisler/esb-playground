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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/21/15
 */
@Named
@Singleton
public class PublishProductService {

    private static final String PI_ENDPOINT = "http://localhost:8080/esb-gateway/api/product";
    private static final Logger logger = Logger.getLogger(PublishProductService.class.getSimpleName());

    private static final Random random = new Random();

    private ThreadPoolExecutor executor;

    @Inject
    private TrackingService trackingService;

    public String batchSend(int count) {
        String batchId = UUID.randomUUID().toString();

        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        executor = new ThreadPoolExecutor( 10, 20, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), threadFactory, new PublisherRejectedExecutionHandler() );

        RunnableMonitor runnableMonitor = new RunnableMonitor( 1000, executor );
        Thread monitorThread = new Thread(runnableMonitor);
        monitorThread.start();

        for (int i = 0; i < count; i++) {
            String msgVersion = i % 2 == 0 ? "4.0" : "2.0";
            try {
                GatewayResponse gatewayResponse = sendAsync(batchId, "jim-sim", msgVersion, generateProductInfo()).get();
                if (gatewayResponse.getErrorItems().isEmpty()) {
                    trackingService.trackSend(batchId, gatewayResponse.getTxId());
                }
                else {
                    logger.warning(gatewayResponse.toString());
                }
            } catch( Exception e ) {
                logger.warning(e.getMessage());
            }
        }

        executor.shutdown();
        while( !executor.isShutdown() ) {
        }
        runnableMonitor.shutdown();

        return batchId;
    }

    Future<GatewayResponse> sendAsync(String batchId, String envName, String msgVersion, ProductInfo productInfo) {
        return executor.submit(
                () -> sendProductInfo(batchId, envName, msgVersion, productInfo )
        );
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

    void snooze() {
        try {
            Thread.sleep( 1000 );
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class PublisherRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            logger.warning( "Rejected execution, sleeping and retrying...." );
            snooze();
            executor.submit( r );
        }
    }
}
