package org.simbiosis.bp.glMsg;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.simbiosis.bp.gl.GlTransMsg;
import org.simbiosis.bp.gl.IGlTransMessaging;

@Stateless
@Remote(IGlTransMessaging.class)
public class GlTransMessaging implements IGlTransMessaging {
	@Resource(mappedName = "java:/ConnectionFactory")
	ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:jboss/queue/transGlIn")
	Queue transGlIn;

	@Override
	public void sendGlTrans(GlTransMsg transMsg) {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(transGlIn);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(transMsg);
			connection.start();
			producer.send(message);
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
