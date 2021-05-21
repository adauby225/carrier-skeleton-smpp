package com.carrier.smpp.pdu.request.dispatching;

import java.util.LinkedList;
import java.util.Queue;

import com.cloudhopper.smpp.pdu.PduRequest;

public class RequestQueue extends RequestCollection{
	private Queue<PduRequest> queueOfPduReq = new LinkedList<>(); 
	
	public RequestQueue() {
		super(RequestQueue.class.getName());
	}
	@Override
	public boolean isEmpty() {
		boolean empty = queueOfPduReq.isEmpty();
		return empty;
	}
	
	@Override
	public long size() {
		long size = queueOfPduReq.size();
		return size;
	}

	@Override
	public void addRequest(PduRequest request) {
		try {
			queueOfPduReq.add(request);
		}catch(Exception e) {
			logger.error(e);
		}
		
	}

	@Override
	public PduRequest nextRequest() {
		PduRequest request = queueOfPduReq.poll();
		return request;
	}
	
}
