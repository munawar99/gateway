package com.jpmorgan.asset.gateway;

import java.lang.Integer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * @author Munawar Akbar
 */
public class MessageGateway implements Gateway {
	
	private static Logger logger = Logger.getLogger(MessageGateway.class);
	private Comparator comparator = new MessageComparator();
	private BlockingQueue<Runnable> blockingQueue;
	private ThreadPoolExecutor executor;
	private List<String> groupReceivedSequenceList = new ArrayList<String>();
	private AtomicInteger atomicInteger = new AtomicInteger();
	
	public MessageGateway() {

		InputStream inputStream;
		Properties properties = new Properties();
		int threadCount = 1;
		int queueSize = 100;
		
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream("default.properties");
			properties.load(inputStream);
			inputStream.close();
			threadCount = Integer.parseInt(properties.getProperty("gateway.threads"));
			queueSize = Integer.parseInt(properties.getProperty("gateway.queuesize"));
		} catch (FileNotFoundException e) {
			String msg = "The configuration file was not found. Check the log file for details. ";
			System.out.println(msg);
			logger.error(msg + e);
		} catch (IOException e) {
			String msg = "The configuration file could not be read. Check the log file for details. ";
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
			logger.debug("Sending symbol " + message.getPayload() + " ...");
		}
		
		executor.execute(runner);
	}
	
}