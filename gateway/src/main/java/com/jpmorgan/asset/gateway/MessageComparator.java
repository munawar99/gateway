package com.jpmorgan.asset.gateway;

import java.util.Comparator;
import org.apache.log4j.Logger;

/**
 * @author Munawar Akbar
 */
public class MessageComparator implements Comparator {
		
	private static Logger logger = Logger.getLogger(MessageComparator.class);

	public int compare(Object x, Object y) {
		
		// Compare by group arrival sequence and then message arrival sequence.
		String grpSeqX = ((Message)((MessageCallback)x).getMessage()).getHeader("SYSTEM-grpReceivedSeq");
        String grpSeqY = ((Message)((MessageCallback)y).getMessage()).getHeader("SYSTEM-grpReceivedSeq");
        int comparison = grpSeqX.compareTo(grpSeqY);

        if (comparison != 0) {
           return comparison;
        } else {
           String msgSeqX = ((Message)((MessageCallback)x).getMessage()).getHeader("SYSTEM-msgReceivedSeq");
           String msgSeqY = ((Message)((MessageCallback)y).getMessage()).getHeader("SYSTEM-msgReceivedSeq");
           return msgSeqX.compareTo(msgSeqY);
        }
    }
	
}