package com.jpmorgan.asset.gateway;

public interface Gateway {

	/**
	 * Send a service reguest.
	 */
	void send(Message msg);

}