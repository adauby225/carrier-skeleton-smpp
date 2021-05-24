package com.carrier.smpp.handler.pdu.request;

import java.util.HashMap;
import java.util.Map;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;



public class SmscRequestHandlerFactory {
	private Map<Integer, RequestHandler<PduRequest,PduResponse>>handlers = new HashMap<>();
	public SmscRequestHandlerFactory(Map<Integer, RequestHandler<PduRequest,PduResponse>>handlers) {
		this.handlers = handlers;
	}

	public RequestHandler<PduRequest,PduResponse> getHandler(int cmdId) {
		return handlers.getOrDefault(cmdId,new DefaultSmscRequestHandler());
	}

}
