package org.gislers.playgrounds.esb.service.dispatch.bean;

import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.service.dispatch.DispatchService;
import org.gislers.playgrounds.esb.service.dispatch.dto.DispatchServiceDto;
import org.gislers.playgrounds.esb.service.dispatch.exception.DispatchServiceException;
import org.gislers.playgrounds.esb.service.dispatch.service.EndpointLookupService;

import javax.ejb.Asynchronous;
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

    private static final Logger logger = Logger.getLogger(DispatchServiceBean.class.getSimpleName());

    @Inject
    private EndpointLookupService endpointLookupService;

    @Override
    @Asynchronous
    public void dispatchMessage(DispatchServiceDto dispatchServiceDto) {
        String endpoint = endpointLookupService.findEndpoint(dispatchServiceDto);
        if( isBlank(endpoint) ) {
            logger.warning("Endpoint not found: " + dispatchServiceDto.toString());
            throw new DispatchServiceException("Endpoint not configured: " + dispatchServiceDto.toString());
        }
        else {
            Response response = sendMessage(endpoint, dispatchServiceDto);
            if (response != null && !isSuccess(response)) {
                logger.info(dispatchServiceDto.getPayload() + " - " + response.toString());
            }
        }
    }

    Response sendMessage(String endpoint, DispatchServiceDto dispatchServiceDto) {
        return ClientBuilder.newClient()
                .target(endpoint)
                .request()
                .header(MessageConstants.TRANSACTION_ID, dispatchServiceDto.getTxId())
                .post(Entity.entity(dispatchServiceDto.getPayload(), MediaType.APPLICATION_JSON_TYPE), Response.class);
    }

    boolean isSuccess( Response response ) {
        Response.Status responseStatus = Response.Status.fromStatusCode(response.getStatus());
        return  responseStatus == Response.Status.ACCEPTED
                || responseStatus == Response.Status.OK;
    }
}
