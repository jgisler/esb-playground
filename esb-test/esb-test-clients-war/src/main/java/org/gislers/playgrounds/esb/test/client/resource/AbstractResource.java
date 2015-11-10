package org.gislers.playgrounds.esb.test.client.resource;

import org.gislers.playgrounds.esb.common.http.ClientEndpointResponse;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;
import org.gislers.playgrounds.esb.test.client.dao.AuditReceivedDao;
import org.gislers.playgrounds.esb.test.client.dao.AuditSentDao;
import org.gislers.playgrounds.esb.test.client.entity.AuditReceivedEntity;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public abstract class AbstractResource {

    @Inject
    private Logger logger;

    protected abstract String getClient();

    @EJB
    private AuditReceivedDao auditReceivedDao;

    @EJB
    private AuditSentDao auditSentDao;

    @POST
    @Path("/product/v3")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveV3( @HeaderParam(MessageConstants.TRANSACTION_ID) String txId,
                               ProductInfo productInfo) {

        auditReceived( txId, System.currentTimeMillis() );

        ClientEndpointResponse clientEndpointResponse = new ClientEndpointResponse();
        clientEndpointResponse.setService("product/v3");
        clientEndpointResponse.setClient(getClient());
        clientEndpointResponse.setTxId(txId);

        return Response.accepted()
                .entity(clientEndpointResponse)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @POST
    @Path("/product/v4")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveV4( @HeaderParam(MessageConstants.TRANSACTION_ID) String txId,
                               ProductInfo productInfo) {

        auditReceived( txId, System.currentTimeMillis() );

        ClientEndpointResponse clientEndpointResponse = new ClientEndpointResponse();
        clientEndpointResponse.setService("product/v4");
        clientEndpointResponse.setClient(getClient());
        clientEndpointResponse.setTxId(txId);

        return Response.accepted()
                .entity(clientEndpointResponse)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    protected void auditReceived( String txId, long timestamp ) {
        AuditReceivedEntity auditReceivedEntity = new AuditReceivedEntity();
        auditReceivedEntity.setId(UUID.randomUUID().toString());
        auditReceivedEntity.setTimestamp( timestamp );
        auditReceivedEntity.setAuditSentEntity( auditSentDao.fetch(txId) );
        auditReceivedDao.create( auditReceivedEntity );
    }
}
