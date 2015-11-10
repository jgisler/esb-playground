package org.gislers.playgrounds.esb.consumer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 11/10/15
 */
public class Resources {

    @Produces
    public Logger logger(InjectionPoint injectionPoint) {
        return Logger.getLogger( injectionPoint.getMember().getDeclaringClass().getName() );
    }
}
