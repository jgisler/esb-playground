package org.gislers.playgrounds.esb.service.dispatch.service;

import org.gislers.playgrounds.esb.service.dispatch.dto.DispatchServiceDto;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@Named
@Singleton
public class EndpointLookupService {

    public String findEndpoint( DispatchServiceDto dispatchServiceDto ) {
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
