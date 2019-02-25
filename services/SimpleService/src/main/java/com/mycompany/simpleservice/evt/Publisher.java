package com.mycompany.simpleservice.evt;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

@Stateless
public class Publisher {

	private String initialContextFactory;

	private String initialContextLookupProtocol;
	
	private String jmsHost;
	
	private String jmsPort;
	
	private String remoteConnectionFactoryJndiName;
	
	private String topicJndiName;
	
	private String providerUrl;
	
	@PostConstruct
	public void init() {
		initialContextFactory = System.getProperty("INITIAL_CONTEXT_FACTORY", "\"org.jboss.naming.remote.client.InitialContextFactory");
		initialContextLookupProtocol = System.getProperty("CONTEXT_LOOKUP_PROTOCOL", "http-remoting");
		jmsHost = System.getProperty("JMS.HOST", "jms");
		jmsPort = System.getProperty("JMS.PORT", "8080");
		remoteConnectionFactoryJndiName = System.getProperty("JMS.CONNECTION.FACTORY.JNDI.NAME", "jms/RemoteConnectionFactory");
		topicJndiName = System.getProperty("JMS.TOPIC.JNDI.NAME", "jms/topic/EventTopic");
		providerUrl = initialContextLookupProtocol + "://" + jmsHost + ":" + jmsPort;
	}
	
	public void publish() {
		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
			props.put(Context.PROVIDER_URL, providerUrl);
			InitialContext context = new InitialContext(props);
			

			javax.jms.Topic topic = (javax.jms.Topic)context.lookup(topicJndiName);
			
			javax.jms.ConnectionFactory connectionFactory = (javax.jms.ConnectionFactory)context.lookup(remoteConnectionFactoryJndiName);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);

            // Clean up
            session.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
	}
	
}
