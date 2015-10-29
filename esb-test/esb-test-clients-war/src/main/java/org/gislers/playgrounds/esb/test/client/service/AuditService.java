package org.gislers.playgrounds.esb.test.client.service;

import org.gislers.playgrounds.esb.test.client.dto.AuditServiceDto;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by:   jgisle
 * Created date: 10/29/15
 */
@Named
@Singleton
public class AuditService {

    private static final ConcurrentHashMap<String, AuditServiceDto> auditMap = new ConcurrentHashMap<>();

    public void auditSent( String txId ) {
        if( !auditMap.containsKey(txId) ) {
            auditMap.put( txId, new AuditServiceDto() );
        }
    }

    public void auditReceived( String txId ) {
        auditMap.get(txId).getReceivedTimestamps().add( System.currentTimeMillis() );
    }

    public AuditServiceDto getAudit( String txId ) {
        return auditMap.get(txId);
    }
}
