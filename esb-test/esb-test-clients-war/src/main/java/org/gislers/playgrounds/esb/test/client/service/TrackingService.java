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

    public void track( String batchId, String txId, long duration ) {
        AtomicLong value;
        while( (value = getOrAdd(batchId, txId)) == null ) {
            snooze();
        }
        value.getAndSet(duration);
    }

    AtomicLong getOrAdd( String batchId, String txId ) {
        if( !(trackingMap.containsKey(batchId)) ) {
            trackingMap.put( batchId, new ConcurrentHashMap<>() );
        }

        if( !(trackingMap.get(batchId).containsKey( txId )) ) {
            trackingMap.get(batchId).put( txId, new AtomicLong(0) );
        }

        return trackingMap.get( batchId ).get( txId );
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

    void snooze() {
        try {
            Thread.sleep( 20l );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
