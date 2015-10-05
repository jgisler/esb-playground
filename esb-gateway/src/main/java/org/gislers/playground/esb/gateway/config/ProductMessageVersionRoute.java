package org.gislers.playground.esb.gateway.config;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.builder.RouteBuilder;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by:   jgisle
 * Created date: 10/5/15
 */
@Startup
@ApplicationScoped
public class ProductMessageVersionRoute extends RouteBuilder {

    private static String BROKER_URL = "vm://localhost?broker.persistent=true&broker.useJmx=true&broker.useShutdownHook=true";

    @Override
    public void configure() throws Exception {

       /**
        * Configure the ActiveMQ component to use an embedded VM transport broker
        */
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setBrokerURL(BROKER_URL);
        getContext().addComponent("activemq", activeMQComponent);

        from("activemq:queue:InboundProductQueue")
            .choice()
                .when( header(Constants.MESSAGE_VERSION).isEqualTo("4.0") )
                    .to( "activemq:topic:OutboundV4ProductTopic" )
                .when( header(Constants.MESSAGE_VERSION).isEqualTo("3.0") )
                    .to( "activemq:topic:OutboundV3ProductTopic" );
    }
}
