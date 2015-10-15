package org.gislers.playgrounds.esb.service.dispatch.service;

import org.gislers.playgrounds.esb.service.dispatch.dto.DispatchServiceDto;

import javax.inject.Named;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@Named
public class EndpointLookupService {

    public String findEndpoint( DispatchServiceDto dispatchServiceDto ) {
        StringBuilder sb = new StringBuilder( "http://localhost:8080/client-endpoints/" )
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
