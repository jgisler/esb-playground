package org.gislers.playground.esb.service.endpoint.impl;

import org.gislers.playground.esb.common.model.Endpoint;
import org.gislers.playground.esb.service.endpoint.EndpointService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/8/15
 */
@Stateless
public class EndpointServiceImpl implements EndpointService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Override
    public Endpoint getEndpoint(String businessName, String serviceName, String envName) {
        Endpoint endpoint = new Endpoint();
        endpoint.setEndpoint( "https://esbdevv-esb-digital.nikedev.com/esb-sim/echo/200" );
        return endpoint;
    }
}
