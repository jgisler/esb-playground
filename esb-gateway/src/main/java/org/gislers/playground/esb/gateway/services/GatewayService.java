package org.gislers.playground.esb.gateway.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playground.esb.common.Product;
import org.gislers.playground.esb.gateway.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

/**
 * Created by jim
 * Created on 10/4/15.
 */
@Stateless
public class GatewayService {

    private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

    @Resource
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:/jms/esb/queue/InboundProductQueue")
    private Queue inboundProductQueue;

    public UUID sendProduct(final String envName, final String messageVersion, final Product product) throws JMSException {

        UUID txId = UUID.randomUUID();

        Connection connection = null;
        Session session = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer messageProducer = session.createProducer(inboundProductQueue);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage textMessage = session.createTextMessage(getJsonPayload(product));
            textMessage.setStringProperty("TRANSACTION_ID", txId.toString());
            textMessage.setStringProperty("ENV_NAME", envName);
            textMessage.setStringProperty("MESSAGE_VERSION", messageVersion);

            messageProducer.send(textMessage);
        } finally {
            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        }


        logger.info(String.format("Sent txId: '%s'", txId.toString()));
        return txId;
    }

    String getJsonPayload(Product product) {
        String payload;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            payload = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            logger.error(ExceptionUtils.getRootCauseMessage(e));
            throw new BadRequestException(e);
        }
        return payload;
    }
}
