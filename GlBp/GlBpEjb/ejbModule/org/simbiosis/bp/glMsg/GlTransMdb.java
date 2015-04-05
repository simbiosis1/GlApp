package org.simbiosis.bp.glMsg;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.simbiosis.bp.gl.GlTransMsg;
import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.gl.model.GlTrans;

/**
 * Message-Driven Bean implementation class for: GlTrans
 * 
 */
@MessageDriven(name = "GlTransMdb", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/queue/transGlIn"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class GlTransMdb implements MessageListener {

	@EJB(lookup = "java:module/GlBp")
	IGlBp ledger;

	/**
	 * Default constructor.
	 */
	public GlTransMdb() {
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		GlTransMsg transMsg;
		try {
			transMsg = (GlTransMsg) objectMessage.getObject();
			GlTrans transDto = transMsg.getGlTransDto();
			ledger.saveGLTrans(transDto);
			if (transDto.getReleased() == 1) {
				ledger.releaseGLTrans(transDto);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
