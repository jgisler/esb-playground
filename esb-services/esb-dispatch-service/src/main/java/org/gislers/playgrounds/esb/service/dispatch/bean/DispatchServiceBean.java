package org.gislers.playgrounds.esb.service.dispatch.bean;

import org.gislers.playgrounds.esb.common.http.Header;
import org.gislers.playgrounds.esb.service.dispatch.DispatchService;
import org.gislers.playgrounds.esb.service.dispatch.dto.DispatchServiceDto;
import org.gislers.playgrounds.esb.service.dispatch.exception.DispatchServiceException;
import org.gislers.playgrounds.esb.service.dispatch.service.EndpointLookupService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@Stateless
public class DispatchServiceBean implements DispatchService {

    private Logger logger = Logger.getLogger( getClass().getSimpleName() );

    private EndpointLookupService endpointLookupService;

    @Inject
    public void setEndpointLookupService(EndpointLookupService endpointLookupService) {
        this.endpointLookupService = endpointLookupService;
    }

    @Override
    public void dispatchMessage(DispatchServiceDto dto) {
        String endpoint = endpointLookupService.findEndpoint(dto);
        if( isBlank(endpoint) ) {
            logger.warning( "Endpoint not found: " + dto.toString() );
            throw new DispatchServiceException("Endpoint not configured: " + dto.toString());
        }
        else {
            Response response = sendMessage(endpoint, dto.getTxId(), dto.getTimestamp(), dto.getPayload());
            if( !isSuccess(response) ) {
                logger.info(dto.getPayload() + " - " + response.toString());
            }
        }
    }

    Response sendMessage( String endpoint, String txId, long timestamp, String payload ) {
        return ClientBuilder.newClient()
                .target(endpoint)
                .request()
                .header(Header.ESB_TX_ID.name(), txId)
                .post(Entity.entity(payload, MediaType.APPLICATION_JSON_TYPE), Response.class);
    }

    boolean isSuccess( Response response ) {
        Response.Status responseStatus = Response.Status.fromStatusCode(response.getStatus());
        return  responseStatus == Response.Status.ACCEPTED
                || responseStatus == Response.Status.OK;
    }
}
