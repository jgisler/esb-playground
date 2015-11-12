package org.gislers.playgrounds.esb.test.client.dao;

import org.gislers.playgrounds.esb.test.client.entity.AuditReceivedEntity;

import javax.ejb.Local;

/**
 * Created by:   jgisle
 * Created date: 11/4/15
 */
@Local
public interface AuditReceivedDao {

    void create(AuditReceivedEntity auditMessageReceivedEntity);
}
