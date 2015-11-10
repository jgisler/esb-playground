package org.gislers.playgrounds.esb.consumer.mdb.product;

import org.gislers.playgrounds.esb.common.message.ClientName;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@MessageDriven(name = "BrandProductV3Mdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup"        , propertyValue = "java:/jms/topic/OutboundV3ProductTopic"),
        @ActivationConfigProperty(propertyName = "destinationType"          , propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode"          , propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionName"         , propertyValue = "BrandProductV3Mdb"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability"   , propertyValue = "Durable")
})
public class BrandProductV3Mdb extends AbstractProductMdb {

    @Inject
    private Logger logger;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected ClientName getConsumerName() {
        return ClientName.BRAND;
    }
}
