package org.gislers.playgrounds.esb.consumer.mdb;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.esb.common.message.ClientName;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.message.ServiceName;
import org.gislers.playgrounds.esb.consumer.exception.EsbConsumerException;
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

    protected abstract Logger       getLogger();
    protected abstract ServiceName  getServiceName();
    protected abstract ClientName   getConsumerName();

    @EJB
    protected DispatchService dispatchService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {

            DispatchServiceDto dispatchServiceDto = new DispatchServiceDto.Builder()
                    .serviceName(getServiceName())
                    .clientName(getConsumerName())
                    .environmentName(textMessage.getStringProperty(MessageConstants.ENV_NAME))
                    .messageVersion(textMessage.getStringProperty(MessageConstants.MESSAGE_VERSION))
                    .txId(textMessage.getStringProperty(MessageConstants.TRANSACTION_ID))
                    .payload(textMessage.getText())
                    .build();

            getLogger().info( "[txId='" + dispatchServiceDto.getTxId() +
                    "', envName='" + dispatchServiceDto.getEnvironmentName() +
                    "', msgVer='" + dispatchServiceDto.getMessageVersion() + "']" );

            dispatchService.dispatchMessage( dispatchServiceDto );
        }
        catch (JMSException e) {
            getLogger().warning(ExceptionUtils.getRootCauseMessage(e));
            throw new EsbConsumerException(e);
        }
    }
}