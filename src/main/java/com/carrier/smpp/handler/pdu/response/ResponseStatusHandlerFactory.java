package com.carrier.smpp.handler.pdu.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseStatusHandlerFactory { 
	private Map<Integer,ResponseHandler>statusHandlersMap = new HashMap<>();

	public ResponseStatusHandlerFactory(Map<Integer, ResponseHandler> statusHandlersMap) {
		super();
		this.statusHandlersMap = statusHandlersMap;
	}
	
	public ResponseHandler getHandler(int status) {
		return statusHandlersMap.getOrDefault(status,new DefaultStatusHandler());
	}
	
	

}
