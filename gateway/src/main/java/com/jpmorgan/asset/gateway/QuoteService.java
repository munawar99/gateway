package com.jpmorgan.asset.gateway;

import java.math.BigDecimal;
import org.apache.log4j.Logger;

/**
 * @author Munawar Akbar
 */
public class QuoteService implements Service, Runnable {

	private static Logger logger = Logger.getLogger(QuoteService.class);
	private final String name;
	
	public QuoteService(String name) {
		this.name = name;
	}
	
	/**
	 * @param name the stock symbol
	 * @return a BigDecimal price
	 */
	public BigDecimal getPrice(String name) {

		BigDecimal price = null;
		
		// Hard code quotes just to demonstrate an external service returning data.
		if (name == "NTAP") {
			price = new BigDecimal("10.10");
		} else if (name == "BVSN") {
			price = new BigDecimal("20.20");
		} else if (name == "ATW") {
			price = new BigDecimal("30.30");
		} else if (name == "RFMD") {
			price = new BigDecimal("40.40");
		} else if (name == "NVDA") {
			price = new BigDecimal("50.50");
		} else if (name == "EXIM") {
			price = new BigDecimal("60.60");
		} else if (name == "SCON") {
			price = new BigDecimal("70.70");
		} else if (name == "NVDA") {
			price = new BigDecimal("80.80");
		}

		return price;
	}
	
	public void run() {
		
		BigDecimal price = getPrice(this.name);
		
		try {
			// Simulate a long-running process.
			Thread.sleep(5000); 
		} catch (InterruptedException e) {
			// No need to do anything.
		} 
		
		String message = "Symbol " + name + " is " + price;
		if (logger.isDebugEnabled()) {
			message += " in thread " + Thread.currentThread().getName();
		}
		logger.info(message);
	}

}