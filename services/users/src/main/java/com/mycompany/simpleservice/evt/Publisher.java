package com.mycompany.simpleservice.evt;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

@Stateless
public class Publisher {

	private static final String DEFAULT_INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	
	private static final String DEFAULT_CONTEXT_LOOKUP_PROTOCOL = "http-remoting";
	
	private static final String DEFAULT_JMS_HOST = "jms";
	
	private static final String DEFAULT_JMS_PORT = "8080";
	
	private static final String DEFAULT_JMS_CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";
	
	private static final String DEFAULT_JMS_TOPIC_JNDI_NAME = "jms/topic/EventTopic";
	
	private String initialContextFactory;

	private String initialContextLookupProtocol;
	
	private String jmsHost;
	
	private String jmsPort;
	
	private String remoteConnectionFactoryJndiName;
	
	private String topicJndiName;
	
	private String providerUrl;
	
	@PostConstruct
	public void init() {
		initialContextFactory = System.getProperty("INITIAL.CONTEXT.FACTORY", DEFAULT_INITIAL_CONTEXT_FACTORY);
		initialContextLookupProtocol = System.getProperty("CONTEXT.LOOKUP.PROTOCOL", DEFAULT_CONTEXT_LOOKUP_PROTOCOL);
		jmsHost = System.getProperty("JMS.HOST", DEFAULT_JMS_HOST);
		jmsPort = System.getProperty("JMS.PORT", DEFAULT_JMS_PORT);
		remoteConnectionFactoryJndiName = System.getProperty("JMS.CONNECTION.FACTORY.JNDI.NAME", DEFAULT_JMS_CONNECTION_FACTORY_JNDI_NAME);
		topicJndiName = System.getProperty("JMS.TOPIC.JNDI.NAME", DEFAULT_JMS_TOPIC_JNDI_NAME);
		providerUrl = initialContextLookupProtocol + "://" + jmsHost + ":" + jmsPort;
	}
	
//	@Resource(lookup = "java:/jms/topic/EventTopic")
//	private Destination destination;
//	
//	@Resource(lookup = "java:/ConnectionFactory")
//	private ConnectionFactory cxf;
	
	public void publish(String messageText) {
		
		
		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
			props.put(Context.PROVIDER_URL, providerUrl);
			InitialContext context = new InitialContext(props);
			
 
			javax.jms.Topic topic = (javax.jms.Topic)context.lookup(topicJndiName);
			
			javax.jms.ConnectionFactory connectionFactory = (javax.jms.ConnectionFactory)context.lookup(remoteConnectionFactoryJndiName);

			
			
            // Create a Connection
            Connection connection = connectionFactory.createConnection();
			
//            System.out.println("DESTINATION::::::::::::::::" + destination);
//			System.out.println("CONNECTIONFACTORY:::::::::::" + cxf);
			System.out.println("DESTINATION::::::::::::::::" + topic);
			System.out.println("CONNECTIONFACTORY:::::::::::" + connectionFactory);
//			
//			Connection connection = cxf.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a MessageProducer from the Session to the Topic or Queue
//            MessageProducer producer = session.createProducer(destination);
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            TextMessage message = session.createTextMessage(messageText);

            // Tell the producer to send the message
            System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName() + "---" + messageText);
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
