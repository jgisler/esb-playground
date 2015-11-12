package org.gislers.playgrounds.esb.consumer.ejb;

import org.gislers.playgrounds.esb.consumer.dto.DispatchServiceDto;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */

@Local
@Stateless
public class EndpointLookupEjb {

    private static final Logger logger = Logger.getLogger( EndpointLookupEjb.class.getName() );

    public String lookupEndpoint( DispatchServiceDto dispatchServiceDto ) {
        StringBuilder sb = new StringBuilder("http://127.0.0.1:8080/esb-test/api/")
                .append(dispatchServiceDto.getClientName().name().toLowerCase()).append("/")
                .append(dispatchServiceDto.getServiceName().name().toLowerCase()).append("/");

        if( "4.0".equalsIgnoreCase(dispatchServiceDto.getMessageVersion()) ) {
            sb.append( "v4" );
        }
        else {
            sb.append( "v3" );
        }

        return sb.toString();
    }
}
