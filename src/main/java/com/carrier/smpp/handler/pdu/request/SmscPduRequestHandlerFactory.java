package com.carrier.smpp.handler.pdu.request;

import java.util.HashMap;
import java.util.Map;

import com.cloudhopper.smpp.pdu.PduRequest;



public class SmscPduRequestHandlerFactory {
	private Map<Integer, RequestHandler>handlers = new HashMap<>();
	public SmscPduRequestHandlerFactory(Map<Integer, RequestHandler>handlers) {
		this.handlers = handlers;
	}

	public RequestHandler<PduRequest> getHandler(int cmdId) {
		return handlers.getOrDefault(cmdId,new UnhandleRequest());
	}

}
