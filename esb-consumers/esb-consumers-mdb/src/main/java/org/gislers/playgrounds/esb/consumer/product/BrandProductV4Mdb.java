package org.gislers.playgrounds.esb.consumer.product;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@MessageDriven(name = "BrandProductV4Mdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup"        , propertyValue = "java:/jms/topic/OutboundV4ProductTopic"),
        @ActivationConfigProperty(propertyName = "destinationType"          , propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode"          , propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionName"         , propertyValue = "BrandProductV4Mdb"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability"   , propertyValue = "Durable")
})
public class BrandProductV4Mdb extends AbstractProductMdb {

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getSimpleName());
    }
}
