package org.gislers.playgrounds.esb.consumer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.esb.common.message.ClientName;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.message.ServiceName;
import org.gislers.playgrounds.esb.service.dispatch.DispatchService;
import org.gislers.playgrounds.esb.service.dispatch.dto.DispatchServiceDto;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public abstract class AbstractEsbMdb implements MessageListener {

    protected DispatchService dispatchService;

    @EJB
    public void setDispatchService(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    protected abstract Logger getLogger();

    protected abstract ServiceName getServiceName();
    protected abstract ClientName getConsumerName();

    @Override
    public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage) message;
        try {
            String txId = textMessage.getStringProperty(MessageConstants.TRANSACTION_ID);
            String envName = textMessage.getStringProperty(MessageConstants.ENV_NAME);
            String messageVersion = textMessage.getStringProperty(MessageConstants.MESSAGE_VERSION);
            String payload = textMessage.getText();

            DispatchServiceDto dispatchServiceDto = new DispatchServiceDto();
            dispatchServiceDto.setServiceName( getServiceName() );
            dispatchServiceDto.setClientName( getConsumerName() );
            dispatchServiceDto.setMessageVersion( messageVersion );
            dispatchServiceDto.setEnvironmentName( envName );
            dispatchServiceDto.setTxId( txId );
            dispatchServiceDto.setPayload( payload );

            dispatchService.dispatchMessage( dispatchServiceDto );
        }
        catch (JMSException e) {
            getLogger().warning(ExceptionUtils.getRootCauseMessage(e));
        }
    }
}
