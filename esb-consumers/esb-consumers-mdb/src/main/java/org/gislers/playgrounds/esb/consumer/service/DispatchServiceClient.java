package org.gislers.playgrounds.esb.consumer.service;

import org.gislers.playgrounds.esb.service.dispatch.DispatchService;
import org.gislers.playgrounds.esb.service.dispatch.dto.DispatchServiceDto;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Created by:   jgisle
 * Created date: 11/11/15
 */
@Named
@Singleton
public class DispatchServiceClient {

    private DispatchService dispatchService;

    @PostConstruct
    private void init() throws NamingException {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);

        dispatchService = (DispatchService) context.lookup( "ejb:esb-dispatch-service-ear/esb-dispatch-service-ejb/DispatchServiceBean!" +
                DispatchService.class.getName() );
    }

    public void dispatch(DispatchServiceDto dispatchServiceDto) {
        dispatchService.dispatchMessage( dispatchServiceDto );
    }
}
