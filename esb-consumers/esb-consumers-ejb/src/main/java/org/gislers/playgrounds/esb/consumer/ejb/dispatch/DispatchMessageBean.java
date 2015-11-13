package org.gislers.playgrounds.esb.consumer.ejb.dispatch;

import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.consumer.ejb.dispatch.dto.DispatchDto;
import org.gislers.playgrounds.esb.consumer.ejb.dispatch.exception.DispatchMessageException;
import org.gislers.playgrounds.esb.consumer.ejb.dispatch.service.EndpointLookupService;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
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
@Local
@Stateless
public class DispatchMessageBean implements DispatchMessage {

    @Inject
    private Logger logger;

    @Inject
    private EndpointLookupService endpointLookupService;

    public void dispatchMessage(DispatchDto dispatchDto) {

        String endpoint = endpointLookupService.lookupEndpoint(dispatchDto);
        if( isBlank(endpoint) ) {
            logger.warning("Endpoint not found: " + dispatchDto.toString());
            throw new DispatchMessageException("Endpoint not configured: " + dispatchDto.toString());
        }
        else {
            Client client = null;
            Response response = null;
            try {
                client = ClientBuilder.newClient();
                response = client.target(endpoint)
                        .request()
                        .header(MessageConstants.TRANSACTION_ID, dispatchDto.getTxId())
                        .post(Entity.entity(dispatchDto.getPayload(), MediaType.APPLICATION_JSON_TYPE), Response.class);

                if (response != null && !isSuccess(response)) {
                    logger.info(dispatchDto.getPayload() + " - " + response.toString());
                }
            }
            finally {
                if( response != null ) {
                    response.close();
                }

                if( client != null ) {
                    client.close();
                }
            }
        }
    }

    boolean isSuccess( Response response ) {
        Response.Status responseStatus = Response.Status.fromStatusCode(response.getStatus());
        return  responseStatus == Response.Status.ACCEPTED
                || responseStatus == Response.Status.OK;
    }
}
