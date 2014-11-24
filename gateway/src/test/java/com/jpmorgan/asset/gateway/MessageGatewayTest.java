package com.jpmorgan.asset.gateway;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Test class for the gateway.
 */
public class MessageGatewayTest {

    /**
     * add(null) throws NPE
     */
    @Test(expected=NullPointerException.class)
    public void testAddNull() {
    	Message message = null;
    	PriorityBlockingQueue queue = new PriorityBlockingQueue(1);
    	queue.add(message);
    }
    
    /**
     * Constructor throws IAE if capacity in the properties file is non-positive
     */
    @Test(expected=IllegalArgumentException.class)
    public void testQueueSizeEmpty() {
    	PriorityBlockingQueue q = new PriorityBlockingQueue(0);
    }
    
    /**
     * Constructor throws IAE if capacity in the properties file is non-existent and therefore null
     */
    @Test(expected=NullPointerException.class)
    public void testQueueSizeNull() {
    	PriorityBlockingQueue q = new PriorityBlockingQueue(null);
    }
 
    /**
     * Check message headers are set
     */
    @Test
    public void testHeadersExist() {
    	Message message = new GenericMessage();
		message.setHeader("SYSTEM-grpReceivedSeq", Integer.toString(10));
		message.setHeader("SYSTEM-msgReceivedSeq", Integer.toString(20));
		Assert.assertNotNull(message.getHeader("SYSTEM-grpReceivedSeq"));
		Assert.assertNotNull(message.getHeader("SYSTEM-msgReceivedSeq"));
    }

}