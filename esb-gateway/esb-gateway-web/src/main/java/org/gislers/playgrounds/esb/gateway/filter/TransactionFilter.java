package org.gislers.playgrounds.esb.gateway.filter;

import org.gislers.playgrounds.esb.common.message.MessageConstants;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by:   jgisle
 * Created date: 10/22/15
 */
@Provider
public class TransactionFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String txId = requestContext.getHeaderString(MessageConstants.TRANSACTION_ID);
        if( isBlank(txId) ) {
            txId = UUID.randomUUID().toString();
            requestContext.getHeaders().add(MessageConstants.TRANSACTION_ID, txId);
        }
    }
}
