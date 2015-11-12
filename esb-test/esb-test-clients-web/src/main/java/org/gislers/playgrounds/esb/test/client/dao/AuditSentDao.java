package org.gislers.playgrounds.esb.test.client.dao;

import org.gislers.playgrounds.esb.test.client.entity.AuditSentEntity;

import javax.ejb.Local;

/**
 * Created by:   jgisle
 * Created date: 11/4/15
 */
@Local
public interface AuditSentDao {

    void create(AuditSentEntity auditEntity);
    AuditSentEntity fetch( String txId );
    void delete(String txId);
}
