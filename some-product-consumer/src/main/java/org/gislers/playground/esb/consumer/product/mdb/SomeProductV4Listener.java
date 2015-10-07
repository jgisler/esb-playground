package org.gislers.playground.esb.consumer.product.mdb;

import org.gislers.playground.esb.common.properties.MessageProperties;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */

@MessageDriven(name = "SomeProductV4MDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup"        , propertyValue = "java:/jms/topic/OutboundV4ProductTopic"),
        @ActivationConfigProperty(propertyName = "destinationType"          , propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode"          , propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionName"         , propertyValue = "SomeProductV4MDB"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability"   , propertyValue = "Durable")
})
public class SomeProductV4Listener implements MessageListener {

    private Logger logger = Logger.getLogger( this.getClass() );

    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;

            String txId = textMessage.getStringProperty(MessageProperties.TRANSACTION_ID);
            String envName = textMessage.getStringProperty(MessageProperties.ENV_NAME);
            String messageVersion = textMessage.getStringProperty(MessageProperties.MESSAGE_VERSION);
            String payload = textMessage.getText();

            String logMsg = "Recieved message: txId='%s', envName='%s', msgVer='%s', payload='%s'";
            logger.info( String.format(logMsg, txId, envName, messageVersion, payload) );
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
