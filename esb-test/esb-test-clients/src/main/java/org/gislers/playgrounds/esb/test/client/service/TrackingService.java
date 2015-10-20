package org.gislers.playgrounds.esb.test.client.service;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by:   jgisle
 * Created date: 10/20/15
 */
@Named
@Singleton
public class TrackingService {

    private ConcurrentHashMap<String, Long> sendMap;
    private ConcurrentHashMap<String, Long> receiveMap;

    @PostConstruct
    private void init() {
        sendMap = new ConcurrentHashMap<>();
        receiveMap = new ConcurrentHashMap<>();
    }

    public void reset() {
        sendMap.clear();
        receiveMap.clear();
    }

    public void trackSend( String txId ) {
        sendMap.put( txId, System.currentTimeMillis() );
    }

    public void trackReceive( String txId ) {
        receiveMap.put( txId, System.currentTimeMillis() );
    }

    public Map<String, String> getStats() {
        Map<String, String> statsMap = new HashMap<>(sendMap.size());
        for( String txId : sendMap.keySet() ) {
            long sentTimestamp = sendMap.get(txId);
            while( receiveMap.get(txId) == null ) {
                snooze();
            }
            long recTimestamp = receiveMap.get(txId);

            statsMap.put(txId, (recTimestamp-sentTimestamp) + "ms");
        }
        return statsMap;
    }

    void snooze() {
        try {
            Thread.sleep(10l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
