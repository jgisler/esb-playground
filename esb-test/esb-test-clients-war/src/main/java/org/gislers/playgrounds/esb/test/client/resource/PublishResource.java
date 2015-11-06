package org.gislers.playgrounds.esb.test.client.resource;

import org.gislers.playgrounds.esb.test.client.dao.AuditSentDao;
import org.gislers.playgrounds.esb.test.client.dto.AuditServiceDto;
import org.gislers.playgrounds.esb.test.client.entity.AuditReceivedEntity;
import org.gislers.playgrounds.esb.test.client.entity.AuditSentEntity;
import org.gislers.playgrounds.esb.test.client.service.PublishProductService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/19/15
 */
@Path("/publish")
public class PublishResource {

    private static final Logger logger = Logger.getLogger(PublishResource.class.getSimpleName());

    @Inject
    private PublishProductService publishProductService;

    @EJB
    private AuditSentDao auditSentDao;

    @GET
    @Path( "/productinfo/{count}" )
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProductInfo( @PathParam("count") int count ) {

        logger.info("Requesting " + count + " messages...");
        List<String> transactionIds = publishProductService.batchSend( count );
        return Response.status(Response.Status.OK)
                .entity(getAuditRecords(transactionIds))
                .build();
    }

    List<AuditServiceDto> getAuditRecords( List<String> txIds ) {
        List<AuditServiceDto> auditServiceDtos = new ArrayList<>(txIds.size());
        for( String txId : txIds ) {

            AuditServiceDto dto = new AuditServiceDto();
            dto.setTxId( txId );

            AuditSentEntity auditSentEntity = getAudit(txId) ;
            for( AuditReceivedEntity auditReceivedEntity : auditSentEntity.getAuditReceivedEntities() ) {
                dto.getTimings().add( auditReceivedEntity.getTimestamp() - auditSentEntity.getTimestamp() );
            }

            auditServiceDtos.add( dto );
            auditSentDao.delete(txId);
        }
        return auditServiceDtos;
    }

    AuditSentEntity getAudit( String txId ) {
        int snoozeCount = 0;
        int maxSnooze = 10;

        AuditSentEntity auditSentEntity = null;
        while( snoozeCount < maxSnooze && (auditSentEntity = auditSentDao.fetch(txId)).getAuditReceivedEntities().size() < 2 ) {
            snooze(100);
            snoozeCount++;
        }
        return auditSentEntity;
    }

    void snooze( int duration ) {
        try {
            Thread.sleep( duration );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
