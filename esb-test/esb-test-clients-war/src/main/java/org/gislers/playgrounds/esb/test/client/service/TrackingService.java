package org.gislers.playgrounds.esb.test.client.service;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by:   jgisle
 * Created date: 10/22/15
 */
@Named
@Singleton
public class TrackingService {

    private ConcurrentHashMap<String, ConcurrentHashMap<String, AtomicLong>> trackingMap = new ConcurrentHashMap<>();

    public void trackSend( String batchId, String txId ) {
        if( !(trackingMap.containsKey(batchId)) ) {
            trackingMap.put( batchId, new ConcurrentHashMap<>() );
        }
        trackingMap.get( batchId ).put( txId, new AtomicLong(0) );
    }

    public void trackReceive( String batchId, String txId, long duration ) {
        trackingMap.get(batchId)
                .get(txId)
                .getAndSet(duration);
    }

    public ConcurrentHashMap<String, AtomicLong> getTrackingByBatch( String batchId ) {
        if( trackingMap.containsKey(batchId) ) {
            return trackingMap.get(batchId);
        }
        return null;
    }

    public int getBatchSize( String batchId ) {
        if( trackingMap.containsKey(batchId) ) {
            return trackingMap.get(batchId).size();
        }
        return 0;
    }

    public void deleteBatch( String batchId ) {
        trackingMap.remove( batchId );
    }
}
