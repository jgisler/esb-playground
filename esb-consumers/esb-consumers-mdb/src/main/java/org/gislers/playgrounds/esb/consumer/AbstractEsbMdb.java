package org.gislers.playgrounds.esb.consumer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.esb.common.message.MessageConstants;

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

    protected abstract Logger getLogger();

    @Override
    public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage) message;
        try {
            String txId = textMessage.getStringProperty(MessageConstants.TRANSACTION_ID);
            String envName = textMessage.getStringProperty(MessageConstants.ENV_NAME);
            String messageVersion = textMessage.getStringProperty(MessageConstants.MESSAGE_VERSION);
            String payload = textMessage.getText();

            String logMsg = "[txId='%s', envName='%s', msgVer='%s', payload='%s'] - Message received";
            getLogger().info(String.format(logMsg, txId, envName, messageVersion, payload));
        }
        catch (JMSException e) {
            getLogger().warning(ExceptionUtils.getRootCauseMessage(e));
        }
    }
}
