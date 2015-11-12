package org.gislers.playgrounds.esb.gateway.ejb;

import com.google.gson.Gson;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.common.model.ProductInfo;
import org.gislers.playgrounds.esb.gateway.dto.ProductInfoDto;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 11/12/15
 */
@Local
@Stateless
public class PublishProductEjb {

    @Inject
    private Logger logger;

    @Resource(lookup="java:jboss/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup="java:/jms/queue/InboundProductQueue")
    private Queue inboundProductQueue;

    public void publishProduct( final ProductInfoDto productDto) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            MessageProducer messageProducer = session.createProducer(inboundProductQueue);
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

            TextMessage textMessage = session.createTextMessage( getJsonString(productDto.getProductInfo()) );
            textMessage.setStringProperty(MessageConstants.TRANSACTION_ID, productDto.getTxId());
            textMessage.setStringProperty(MessageConstants.ENV_NAME, productDto.getEnvironmentName());
            textMessage.setStringProperty(MessageConstants.MESSAGE_VERSION, productDto.getMessageVersion());

            messageProducer.send(textMessage);
        }
        finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    logger.info(ExceptionUtils.getRootCauseMessage(e));
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    logger.info(ExceptionUtils.getRootCauseMessage(e));
                }
            }
        }
    }

    private String getJsonString(ProductInfo productInfo) {
        Gson gson = new Gson();
        return gson.toJson( productInfo );
    }
}
