package com.jpmorgan.asset.gateway;

/**
 * @author Munawar Akbar
 *
 */
public class MessageCallback implements Runnable {

	  private final Runnable task;
	  private final Message message;

	  MessageCallback(Runnable task, Message message) {
	    this.task = task;
	    this.message = message;
	  }

	  public Message getMessage() {
		  return this.message;
	  }
	  
	  public void run() {
	    task.run();
	    message.completed();
	  }

}
