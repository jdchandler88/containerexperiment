package com.mycompany.simpleservice.evt;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.ejb3.annotation.ResourceAdapter;

@MessageDriven(activationConfig= {
		@ActivationConfigProperty(propertyName="providerURL", propertyValue="http://localhost:61616"),
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Topic"),
		@ActivationConfigProperty(propertyName="destination", propertyValue="EventTopic")
})
public class Listener implements MessageListener {

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
