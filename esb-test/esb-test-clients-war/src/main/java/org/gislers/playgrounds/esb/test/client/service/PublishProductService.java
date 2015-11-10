package org.gislers.playgrounds.esb.test.client.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.gislers.playgrounds.esb.common.http.GatewayResponse;
import org.gislers.playgrounds.esb.test.client.dao.AuditSentDao;
import org.gislers.playgrounds.esb.test.client.entity.AuditSentEntity;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    @Inject
    private Logger logger;

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

        List<String> txIds = null;
        try {
            monitorThread.start();
            txIds = submitTasks(batchSize, executor);
        }
        catch( Exception e ) {
            logger.warning( ExceptionUtils.getRootCauseMessage(e) );
        }
        finally {
            logger.info( "Shutting down the executor..." );
            executor.shutdown();
            while( !executor.isShutdown() ) {
                snooze(1000);
            }
            runnableMonitor.shutdown();
        }
        return txIds;
    }

    private List<String> submitTasks( int batchSize, ThreadPoolExecutor executor ) {

        List<String> txIds = new ArrayList<>(batchSize);

        int counter = 0;
        while( counter < batchSize ) {

            List<Future<GatewayResponse>> futureResponses = new ArrayList<>();
            for (int i = 0; i<CORE_POOL_SIZE && i<batchSize; i++) {
                futureResponses.add(executor.submit(new PublishMessageTask()));
                counter++;
            }

            for( Future<GatewayResponse> futureResponse : futureResponses ) {
                try {
                    GatewayResponse gatewayResponse = futureResponse.get();
                    if( gatewayResponse != null && HttpStatus.SC_ACCEPTED == gatewayResponse.getHttpStatus() ) {
                        if( gatewayResponse.getErrorItems().isEmpty() ) {
                            txIds.add(gatewayResponse.getTxId());
                            auditSentDao.create(new AuditSentEntity(gatewayResponse.getTxId(), gatewayResponse.getGatewayTimestamp()));
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
}
