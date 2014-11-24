package com.jpmorgan.asset.gateway;

public interface Message {
	
	void setHeader(String name, String header);
	
	String getHeader(String name);
	
	void setPayload(String payload);
	
	String getPayload();
	
	/**
	 * Upon completion of processing.
	 */
	void completed();

}