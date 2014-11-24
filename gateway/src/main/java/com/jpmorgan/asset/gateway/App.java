package com.jpmorgan.asset.gateway;

import org.apache.log4j.Logger;

/**
 * @author Munawar Akbar
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class App {

	private static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {

		Gateway gateway = Factory.getGateway("messageGateway");
		Message message;
		
		try {
			message = new GenericMessage();
			message.setHeader("groupId", "2");
			message.setPayload("NTAP");
			logger.info("Sending group 2 message NTAP ...");
			gateway.send(message);

			message = new GenericMessage();
			message.setHeader("groupId", "1");
			message.setPayload("BVSN");
			logger.info("Sending group 1 message BVSN ...");
			gateway.send(message);
			
			message = new GenericMessage();
			message.setHeader("groupId", "2");
			message.setPayload("ATW");
			logger.info("Sending group 2 message ATW ...");
			gateway.send(message);
			
			message = new GenericMessage();
			message.setHeader("groupId", "3");
			message.setPayload("RFMD");
			logger.info("Sending group 3 message RFMD ...");
			gateway.send(message);
			
			// More.
			
			message = new GenericMessage();
			message.setHeader("groupId", "3");
			message.setPayload("NVDA");
			logger.info("Sending group 3 message NVDA ...");
			gateway.send(message);
			
			message = new GenericMessage();
			message.setHeader("groupId", "1");
			message.setPayload("SCON");
			logger.info("Sending group 1 message SCON ...");
			gateway.send(message);
			
			message = new GenericMessage();
			message.setHeader("groupId", "2");
			message.setPayload("EXIM");
			logger.info("Sending group 2 message EXIM ...");
			gateway.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}