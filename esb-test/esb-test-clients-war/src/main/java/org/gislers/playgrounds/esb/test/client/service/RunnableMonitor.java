package org.gislers.playgrounds.esb.test.client.service;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/26/15
 */
public class RunnableMonitor implements Runnable {

    private static final String MSG_TEMPLATE = "[ corePoolSize=%d, poolSize=%d, largestPoolSize=%d, activeCount=%d, taskCount=%d, completedTaskCount=%d ]";

    private static final Logger logger = Logger.getLogger( RunnableMonitor.class.getSimpleName() );

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

        while( runIt ) {
            logger.info(
                String.format(MSG_TEMPLATE,
                    executor.getCorePoolSize(),
                    executor.getPoolSize(),
                    executor.getLargestPoolSize(),
                    executor.getActiveCount(),
                    executor.getTaskCount(),
                    executor.getCompletedTaskCount())
            );

            try {
                Thread.sleep( delay );
            }
            catch( InterruptedException e ) {
                logger.warning( e.getMessage() );
            }
        }
    }
}
