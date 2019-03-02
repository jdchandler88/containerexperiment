package com.mycompany.simpleservice.evt;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(

	    name = "EventMDB",

	    activationConfig = {

	        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),

	        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/topic/EventTopic"),

	        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
	        
	        @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue="host=jms;port=8080;http-upgrade-enabled=true"),
	        
	        @ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory")

	    })
public class Listener implements MessageListener {

	
	@PostConstruct
	public void init() {
		System.out.println("AAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("RECEIVED MESSAGE--" + message.getBody(String.class));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("ERROR RECEIVING MESSAGE--" + e.getMessage());
		}
	}

}
