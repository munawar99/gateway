package com.jpmorgan.asset.gateway;

import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * @author Munawar Akbar
 */
public class GenericMessage implements Message {
	
	private static Logger logger = Logger.getLogger(GenericMessage.class);
	private HashMap<String, String> headerMap = new HashMap<String, String>();
	private String payload;
	
	public void setHeader(String name, String header) {
		this.headerMap.put(name, header);
	}
	
	public String getHeader(String name) {
		return this.headerMap.get(name);
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getPayload() {
		return this.payload;
	}
	
	public void completed() {
		logger.info("Message " + this.payload + " of group " + this.getHeader("groupId") + " completed");
	}
	
}