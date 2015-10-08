package org.gislers.playground.esb.service.endpoint.impl;

import org.gislers.playground.esb.common.model.Credential;
import org.gislers.playground.esb.service.endpoint.CredentialService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/8/15
 */
@Stateless
public class CredentialServiceImpl implements CredentialService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Override
    public Credential getCredential(String businessName, String serviceName, String envName) {

        Credential credential = new Credential();
        credential.setUsername("username");
        credential.setPassword("password");
        return credential;
    }
}
