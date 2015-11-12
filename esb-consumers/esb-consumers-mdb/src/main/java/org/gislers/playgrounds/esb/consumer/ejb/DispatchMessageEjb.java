package org.gislers.playgrounds.esb.consumer.ejb;

import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.consumer.dto.DispatchServiceDto;
import org.gislers.playgrounds.esb.consumer.exception.EsbConsumerException;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
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
public class DispatchMessageEjb {

    private static final Logger logger = Logger.getLogger( DispatchMessageEjb.class.getName() );

    @EJB
    private EndpointLookupEjb endpointLookupEjb;

    @EJB
    private DispatchMessageEjb dispatchMessageEjb;

    public void dispatchMessage(DispatchServiceDto dispatchServiceDto) {

        String endpoint = endpointLookupEjb.lookupEndpoint(dispatchServiceDto);
        if( isBlank(endpoint) ) {
            logger.warning("Endpoint not found: " + dispatchServiceDto.toString());
            throw new EsbConsumerException("Endpoint not configured: " + dispatchServiceDto.toString());
        }
        else {
            Client client = null;
            Response response = null;
            try {
                client = ClientBuilder.newClient();
                response = client.target(endpoint)
                        .request()
                        .header(MessageConstants.TRANSACTION_ID, dispatchServiceDto.getTxId())
                        .post(Entity.entity(dispatchServiceDto.getPayload(), MediaType.APPLICATION_JSON_TYPE), Response.class);

                if (response != null && !isSuccess(response)) {
                    logger.info(dispatchServiceDto.getPayload() + " - " + response.toString());
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
