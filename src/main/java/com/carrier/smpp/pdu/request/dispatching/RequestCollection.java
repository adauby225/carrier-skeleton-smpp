package com.carrier.smpp.pdu.request.dispatching;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.pdu.PduRequest;

public abstract class RequestCollection {
	protected final Logger logger ; 
	public RequestCollection(String loggerName) {
		logger = LogManager.getLogger(loggerName);
	}
	
	public abstract void addRequest(PduRequest request); 
	public abstract PduRequest nextRequest();
	public abstract boolean isEmpty();
	public abstract long size(); 

}
