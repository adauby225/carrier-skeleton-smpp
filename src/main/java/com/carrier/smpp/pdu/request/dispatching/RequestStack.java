package com.carrier.smpp.pdu.request.dispatching;

import java.util.Stack;

import com.cloudhopper.smpp.pdu.PduRequest;

public class RequestStack extends RequestCollection {
	private Stack<PduRequest> stack = new Stack();
	
	public RequestStack() {
		super(RequestStack.class.getName());
	}
	@Override
	public void addRequest(PduRequest request) {
		stack.push(request);

	}

	@Override
	public PduRequest nextRequest() {
		PduRequest request=null;
		try {
			request = stack.pop();
		}catch(Exception e) {
			logger.error(e);
		}
		return request;
	}

	@Override
	public boolean isEmpty() {
		return stack.stream().count()==0;
	}

	@Override
	public long size() {
		return stack.stream().count();
	}

}
