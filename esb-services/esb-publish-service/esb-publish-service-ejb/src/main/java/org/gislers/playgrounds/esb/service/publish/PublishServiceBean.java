package org.gislers.playgrounds.esb.service.publish;

import com.google.gson.Gson;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.esb.common.message.MessageConstants;
import org.gislers.playgrounds.esb.service.publish.dto.ProductInfoDto;
import org.gislers.playgrounds.esb.service.publish.exception.PublishException;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Remote;
import javax.ejb.Stateless;
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
 * Created date: 10/12/15
 */
@Stateless
@Remote(PublishService.class)
public class PublishServiceBean implements PublishService {

    private static final Logger logger = Logger.getLogger( PublishServiceBean.class.getSimpleName() );

    private static final Gson gson = new Gson();

    @Resource(lookup="java:jboss/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup="java:/jms/queue/InboundProductQueue")
    private Queue inboundProductQueue;

    @Override
    @Asynchronous
    public void publish( final ProductInfoDto productDto) throws PublishException {

        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            MessageProducer messageProducer = session.createProducer(inboundProductQueue);
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

            TextMessage textMessage = session.createTextMessage( gson.toJson(productDto.getProductInfo()) );
            textMessage.setStringProperty(MessageConstants.TRANSACTION_ID   , productDto.getTxId());
            textMessage.setStringProperty(MessageConstants.ENV_NAME         , productDto.getEnvironmentName());
            textMessage.setStringProperty(MessageConstants.MESSAGE_VERSION  , productDto.getMessageVersion());

            messageProducer.send(textMessage);
        }
        catch ( JMSException e ) {
            throw new PublishException(e);
        }
        finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    logger.info(getExceptionRootCause(e));
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    logger.info(getExceptionRootCause(e));
                }
            }
        }
    }

    String getExceptionRootCause( Exception e ) {
        return ExceptionUtils.getRootCauseMessage(e);
    }
}