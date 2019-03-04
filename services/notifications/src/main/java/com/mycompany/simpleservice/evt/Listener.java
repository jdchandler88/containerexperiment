package com.mycompany.simpleservice.evt;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(

	    name = "EventMDB",

	    activationConfig = {

	        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),

	        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic/EventTopic"),

	        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
	        
	        @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue="host=jms;port=8080;httpUpgradeEnabled=true"),
	        
	        @ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory")

	    })
public class Listener implements MessageListener {

	@Inject
	private NotificationStorage notificationStorage;
	
	@Inject
	private WebsocketSessionManager sessionManager;
	
	@Override
	public void onMessage(Message message) {
		try {
			String body = message.getBody(String.class);
			this.notificationStorage.storeNotification(body);
			this.sessionManager.publish(body);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("NOTIFICATIONS PROJ ERROR RECEIVING MESSAGE--" + e.getMessage());
		}
	}

}
