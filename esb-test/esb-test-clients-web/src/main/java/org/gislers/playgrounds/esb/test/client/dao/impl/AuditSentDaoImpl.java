package org.gislers.playgrounds.esb.test.client.dao.impl;

import org.gislers.playgrounds.esb.test.client.dao.AuditSentDao;
import org.gislers.playgrounds.esb.test.client.entity.AuditSentEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 11/4/15
 */
@Stateless
public class AuditSentDaoImpl implements AuditSentDao {

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(AuditSentEntity auditSentEntity) {
        entityManager.persist( auditSentEntity );
    }

    @Override
    public AuditSentEntity fetch(String txId) {
        return entityManager.find( AuditSentEntity.class, txId );
    }

    @Override
    public void delete(String txId) {
        entityManager.remove(fetch(txId));
    }
}
