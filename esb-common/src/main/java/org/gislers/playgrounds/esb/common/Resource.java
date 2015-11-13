package org.gislers.playgrounds.esb.common;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 11/12/15
 */
@Default
public class Resource {

    @Produces
    public Logger logger(InjectionPoint injectionPoint) {
        return Logger.getLogger( injectionPoint.getMember().getDeclaringClass().getName() );
    }
}
