package org.gislers.playgrounds.esb.gateway.service;

import org.gislers.playgrounds.esb.service.publish.PublishService;
import org.gislers.playgrounds.esb.service.publish.dto.ProductInfoDto;

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
public class PublishServiceClient {

    private PublishService publishService;

    @PostConstruct
    private void init() throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);

        publishService = (PublishService) context.lookup( "ejb:esb-publish-service-ear/esb-publish-service-ejb/PublishServiceBean!" +
                PublishService.class.getName() );
    }

    public void publish( ProductInfoDto productInfoDto ) {
        publishService.publish(productInfoDto);
    }
}
