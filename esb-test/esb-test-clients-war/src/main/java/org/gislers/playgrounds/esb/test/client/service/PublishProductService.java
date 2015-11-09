package org.gislers.playgrounds.esb.test.client.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;
import org.gislers.playgrounds.esb.test.client.dao.AuditSentDao;
import org.gislers.playgrounds.esb.test.client.entity.AuditSentEntity;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
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

    private static final String PI_ENDPOINT = "http://127.0.0.1:8080/esb-gateway/api/product";

    private static final Logger logger = Logger.getLogger(PublishProductService.class.getSimpleName());

    private static final int CORE_POOL_SIZE     = 10;
    private static final int MAX_POOL_SIZE      = 20;
    private static final int KEEP_ALIVE_TIME    = 10;

    private Random random;

    @EJB
    private AuditSentDao auditSentDao;

    @PostConstruct
    private void init() {
        random = new Random();
    }

    public List<String> batchSend(int batchSize) {

        ThreadPoolExecutor executor = threadPoolExecutor();

        RunnableMonitor runnableMonitor = new RunnableMonitor( 5000, executor );
        Thread monitorThread = new Thread(runnableMonitor);
        monitorThread.start();

        List<String> txIds = new ArrayList<>(batchSize);

        int counter = 0;
        while( counter < batchSize ) {

            List<Future<GatewayResponse>> futureResponses = new ArrayList<>();
            for (int i = 0; i<CORE_POOL_SIZE && i<batchSize; i++) {
                String msgVersion = i % 2 == 0 ? "4.0" : "2.0";
                futureResponses.add(
                        executor.submit( new PublishTask(msgVersion) )
                );
                counter++;
            }

            for( Future<GatewayResponse> futureResponse : futureResponses ) {
                try {
                    GatewayResponse gatewayResponse = futureResponse.get();
                    if( gatewayResponse != null && HttpStatus.SC_ACCEPTED == gatewayResponse.getHttpStatus() ) {
                        if( gatewayResponse.getErrorItems().isEmpty() ) {
                            txIds.add(gatewayResponse.getTxId());
                        } else {
                            logger.warning(gatewayResponse.toString());
                        }
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
        return txIds;
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

    protected class PublishTask implements Callable<GatewayResponse> {

        private String messageVersion;

        public PublishTask( String messageVersion ) {
            this.messageVersion = messageVersion;
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
                        .header(MessageConstants.MESSAGE_VERSION, messageVersion)
                        .post(Entity.entity(generateProductInfo(), MediaType.APPLICATION_JSON_TYPE), Response.class);

                if (response != null) {
                    gatewayResponse = response.readEntity(GatewayResponse.class);
                    auditSentDao.create(new AuditSentEntity(gatewayResponse.getTxId(), gatewayResponse.getGatewayTimestamp()));
                }
            }
            finally {
                if( response != null ) {
                    response.close();
                }
            }
            return gatewayResponse;
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
}
