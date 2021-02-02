package com.carrier.smpp.pdu.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseStatusHandlerFactory { 
	private Map<Integer,Handlable>statusHandlersMap = new HashMap<>();

	public ResponseStatusHandlerFactory(Map<Integer, Handlable> statusHandlersMap) {
		super();
		this.statusHandlersMap = statusHandlersMap;
	}
	
	public Handlable getHandler(int status) {
		return statusHandlersMap.getOrDefault(status,new DefaultStatusHandler());
	}
	
	

}
