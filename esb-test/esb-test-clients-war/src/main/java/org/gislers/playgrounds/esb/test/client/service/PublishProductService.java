package org.gislers.playgrounds.esb.test.client.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    private static final int CORE_POOL_SIZE     = 10;
    private static final int MAX_POOL_SIZE      = 20;
    private static final int KEEP_ALIVE_TIME    = 10;

    private Random random;

    @PostConstruct
    private void init() {
        random = new Random();
    }

    public String batchSend(int batchSize) {

        ThreadPoolExecutor executor = threadPoolExecutor();

        RunnableMonitor runnableMonitor = new RunnableMonitor( 1000, executor );
        Thread monitorThread = new Thread(runnableMonitor);
        monitorThread.start();

        String batchId = UUID.randomUUID().toString();

        int counter = 0;
        while( counter < batchSize ) {

            List<Future<GatewayResponse>> futureResponses = new ArrayList<>();
            for (int i = 0; i<10 && i<batchSize; i++) {
                String msgVersion = i % 2 == 0 ? "4.0" : "2.0";
                futureResponses.add(
                    executor.submit(
                            () -> ClientBuilder.newClient()
                                    .target(PI_ENDPOINT)
                                    .request()
                                    .header(MessageConstants.ENV_NAME, "jim-sim")
                                    .header(MessageConstants.MESSAGE_VERSION, msgVersion)
                                    .post(Entity.entity(generateProductInfo(), MediaType.APPLICATION_JSON_TYPE), GatewayResponse.class)
                    )
                );
                counter++;
            }

            for( Future<GatewayResponse> futureResponse : futureResponses ) {
                try {
                    GatewayResponse gatewayResponse = futureResponse.get();
                    if( !gatewayResponse.getErrorItems().isEmpty() ) {
                        logger.warning(gatewayResponse.toString());
                    }
                }
                catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        }

        logger.info( "Shutting down the executor..." );
        executor.shutdown();
        while( !executor.isShutdown() ) {
            snooze(1000);
        }
        runnableMonitor.shutdown();

        return batchId;
    }

    void snooze(int duration) {
        try {
            Thread.sleep( duration );
        }
        catch (InterruptedException e) {
            logger.warning( ExceptionUtils.getRootCauseMessage(e) );
        }
    }

    ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(MAX_POOL_SIZE),
                Executors.defaultThreadFactory()
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
}
