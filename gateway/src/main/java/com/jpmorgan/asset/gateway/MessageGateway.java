package com.jpmorgan.asset.gateway;

import java.lang.Integer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Munawar Akbar
 */
public class MessageGateway implements Gateway {
	
	private static Logger logger = Logger.getLogger(MessageGateway.class);
	private Comparator comparator = new MessageComparator();
	private PriorityBlockingQueue<Runnable> blockingQueue;
	private ThreadPoolExecutor executor;
	private List<String> groupReceivedSequenceList = new ArrayList<String>();
	private AtomicInteger atomicInteger = new AtomicInteger();
	
	public MessageGateway() {

		int threadCount = 1;
		int queueSize = 100;
		
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("default.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			inputStream.close();
			threadCount = Integer.parseInt(properties.getProperty("gateway.threads"));
			queueSize = Integer.parseInt(properties.getProperty("gateway.queuesize"));
		} catch (FileNotFoundException e) {
			String msg = "The configuration file was not found. Ask the helpdesk for support. ";
			System.out.println(msg);
			logger.error(msg + e);
		} catch (IOException e) {
			String msg = "The configuration file could not be read. Ask the helpdesk for support. ";
			System.out.println(msg);
			logger.error(msg + e);
		}
		
		// Abstractions for queueing and running tasks in threads.
		blockingQueue = new PriorityBlockingQueue<Runnable>(queueSize, this.comparator);
        executor = new ThreadPoolExecutor(threadCount, threadCount, 5000,
        									TimeUnit.MILLISECONDS, blockingQueue);
		
		if (logger.isDebugEnabled()) {
			logger.debug("The gateway has " + threadCount + " threads and queue size " + queueSize);
		}
	}
	
	public void send(Message message) {
		
		String groupId = message.getHeader("groupId");
		
		if (!this.groupReceivedSequenceList.contains(groupId)) {
			this.groupReceivedSequenceList.add(groupId);
		}
		
		// Add metadata to the message so we can process messages in the order they came in.
		message.setHeader("SYSTEM-grpReceivedSeq", Integer.toString(groupReceivedSequenceList.indexOf(groupId)));
		message.setHeader("SYSTEM-msgReceivedSeq", Integer.toString(atomicInteger.getAndIncrement()));
		
		Runnable runner = new MessageCallback(new QuoteService(message.getPayload()), message);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Processing symbol " + message.getPayload() + " ...");
		}
		
		executor.execute(runner);
	}
	
}