package org.gislers.playgrounds.esb.test.client.dao.impl;

import org.gislers.playgrounds.esb.test.client.dao.AuditReceivedDao;
import org.gislers.playgrounds.esb.test.client.entity.AuditReceivedEntity;

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
public class AuditReceivedDaoImpl implements AuditReceivedDao {

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(AuditReceivedEntity auditReceivedEntity) {
        entityManager.persist( auditReceivedEntity );
    }
}
