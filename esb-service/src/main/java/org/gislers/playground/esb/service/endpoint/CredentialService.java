package org.gislers.playground.esb.service.endpoint;

import org.gislers.playground.esb.common.model.Credential;

import javax.ejb.Local;

/**
 * Created by:   jgisle
 * Created date: 10/8/15
 */
@Local
public interface CredentialService {

    Credential getCredential( String businessName, String serviceName, String envName );
}
