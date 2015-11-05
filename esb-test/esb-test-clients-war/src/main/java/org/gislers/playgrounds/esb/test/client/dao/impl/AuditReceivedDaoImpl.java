package org.gislers.playgrounds.esb.test.client.dao.impl;

import org.gislers.playgrounds.esb.test.client.dao.AuditReceivedDao;
import org.gislers.playgrounds.esb.test.client.entity.AuditReceivedEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by:   jgisle
 * Created date: 11/4/15
 */
@Stateless
public class AuditReceivedDaoImpl implements AuditReceivedDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(AuditReceivedEntity auditReceivedEntity) {
        entityManager.persist( auditReceivedEntity );
    }
}
