package com.carrier.smpp.handler.pdu.request;

import java.util.HashMap;
import java.util.Map;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;



public class PduRequestHandlerFactory {
	private Map<Integer, RequestHandler>handlers = new HashMap<>();
	public PduRequestHandlerFactory(Map<Integer, RequestHandler>handlers) {
		this.handlers = handlers;
	}

	public RequestHandler<PduRequest,PduResponse> getHandler(int cmdId) {
		return handlers.getOrDefault(cmdId,new UnhandleRequest());
	}

}
