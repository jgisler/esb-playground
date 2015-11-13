package org.gislers.playgrounds.esb.consumer.mdb;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.esb.common.message.ClientName;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.message.ServiceName;
import org.gislers.playgrounds.esb.consumer.ejb.dispatch.DispatchMessage;
import org.gislers.playgrounds.esb.consumer.ejb.dispatch.dto.DispatchDto;
import org.gislers.playgrounds.esb.consumer.ejb.dispatch.exception.DispatchMessageException;
import org.gislers.playgrounds.esb.consumer.exception.EsbConsumerException;

import javax.ejb.EJB;
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
    protected DispatchMessage dispatchMessage;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            DispatchDto dispatchServiceDto = new DispatchDto.Builder()
                    .serviceName(getServiceName())
                    .clientName(getConsumerName())
                    .environmentName(textMessage.getStringProperty(MessageConstants.ENV_NAME))
                    .messageVersion(textMessage.getStringProperty(MessageConstants.MESSAGE_VERSION))
                    .txId(textMessage.getStringProperty(MessageConstants.TRANSACTION_ID))
                    .payload(textMessage.getText())
                    .build();

            dispatchMessage.dispatchMessage(dispatchServiceDto);
        }
        catch( Exception e ) {
            getLogger().warning(ExceptionUtils.getRootCauseMessage(e));
            throw new EsbConsumerException(e);
        }
    }
}
