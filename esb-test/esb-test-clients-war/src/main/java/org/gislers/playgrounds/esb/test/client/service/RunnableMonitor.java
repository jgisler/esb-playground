package org.gislers.playgrounds.esb.test.client.service;

import javax.inject.Inject;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/26/15
 */
public class RunnableMonitor implements Runnable {

    private static final String MSG_TEMPLATE = "[poolSize=%d, activeCount=%d, completedTaskCount=%d]";

    @Inject
    private Logger logger;

    private int delay;
    private ThreadPoolExecutor executor;

    private boolean runIt = true;

    public RunnableMonitor( int delay, ThreadPoolExecutor executor ) {
        this.delay = delay;
        this.executor = executor;
    }

    public void shutdown() {
        this.runIt = false;
    }

    @Override
    public void run() {
        logger.info( "Starting monitor thread..." );
        while( runIt ) {
            logger.info(
                String.format(MSG_TEMPLATE,
                    executor.getPoolSize(),
                    executor.getActiveCount(),
                    executor.getCompletedTaskCount())
            );

            try {
                Thread.sleep( delay );
            }
            catch( InterruptedException e ) {
                logger.warning( e.getMessage() );
            }
        }
        logger.info( "Monitor thread stopped..." );
    }
}
