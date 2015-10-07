package org.gislers.playground.esb.gateway.services;

import org.gislers.playground.esb.common.properties.MessageProperties;
import org.gislers.playground.esb.gateway.dto.ProductDto;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by jim
 * Created on 10/4/15.
 */
@Named
public class GatewayService {

    private Logger logger = Logger.getLogger( this.getClass() );

    @Resource(lookup="java:jboss/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup="java:/jms/queue/InboundProductQueue")
    private Queue inboundProductQueue;

    public void sendProduct( final ProductDto productDto ) throws JMSException {

        Connection connection = null;
        Session session = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            MessageProducer messageProducer = session.createProducer(inboundProductQueue);
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

            TextMessage textMessage = session.createTextMessage(productDto.getPayload());
            textMessage.setStringProperty(MessageProperties.ENV_NAME, productDto.getEnvironmentName());
            textMessage.setStringProperty(MessageProperties.TRANSACTION_ID, productDto.getTxId());
            textMessage.setStringProperty(MessageProperties.MESSAGE_VERSION, productDto.getMessageVersion());

            messageProducer.send(textMessage);
            logger.info(String.format("Sent message; txId: '%s'", productDto.getTxId()));
        }
        finally {
            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }
}
