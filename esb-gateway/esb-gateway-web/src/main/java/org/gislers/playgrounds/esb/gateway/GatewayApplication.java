package org.gislers.playgrounds.esb.gateway;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/14/15
 */
@ApplicationPath("/api")
public class GatewayApplication extends Application {

    @Produces
    public Logger logger(InjectionPoint injectionPoint) {
        return Logger.getLogger( injectionPoint.getMember().getDeclaringClass().getName() );
    }

}
