package org.gislers.playgrounds.esb.consumer.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;
import org.gislers.playgrounds.esb.common.message.MessageConstants;

import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */
@Startup
@ApplicationScoped
@ContextName("jms-camel-context")
public class EsbRouteBuilder extends RouteBuilder {

    @Resource(lookup = "java:jboss/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    public void configure() throws Exception {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);

        getContext().addComponent("jms", jmsComponent);

        from("jms:queue:InboundProductQueue")
            .routeId("inbound-product-route")
            .choice()
                .when(header(MessageConstants.MESSAGE_VERSION).isEqualTo("4.0"))
                    .to("jms:topic:OutboundV4ProductTopic")
                .otherwise()
                    .to("jms:topic:OutboundV3ProductTopic")
            .end();
    }
}
