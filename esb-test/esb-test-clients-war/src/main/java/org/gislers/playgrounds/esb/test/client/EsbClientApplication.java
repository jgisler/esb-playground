package org.gislers.playgrounds.esb.test.client;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.logging.Logger;

/**
 * Created by jim
 * Created on 10/17/15.
 */
@ApplicationPath("/api")
public class EsbClientApplication extends Application {

    @Produces
    public Logger logger(InjectionPoint injectionPoint) {
        return Logger.getLogger( injectionPoint.getMember().getDeclaringClass().getName() );
    }
}
