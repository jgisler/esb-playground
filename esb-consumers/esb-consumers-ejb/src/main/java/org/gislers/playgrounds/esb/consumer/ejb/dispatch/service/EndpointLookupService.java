package org.gislers.playgrounds.esb.consumer.ejb.dispatch.service;

import org.gislers.playgrounds.esb.consumer.ejb.dispatch.dto.DispatchDto;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by:   jgisle
 * Created date: 11/12/15
 */
@Named
@Singleton
public class EndpointLookupService {

    public String lookupEndpoint( DispatchDto dispatchDto) {
        StringBuilder sb = new StringBuilder("http://127.0.0.1:8080/esb-test/api/")
                .append(dispatchDto.getClientName().name().toLowerCase()).append("/")
                .append(dispatchDto.getServiceName().name().toLowerCase()).append("/");

        if( "4.0".equalsIgnoreCase(dispatchDto.getMessageVersion()) ) {
            sb.append( "v4" );
        }
        else {
            sb.append( "v3" );
        }

        return sb.toString();
    }
}
