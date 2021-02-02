package com.carrier.smpp.pdu.Handler;

import java.util.HashMap;
import java.util.Map;

public class ResponseStatusHandlerFactory { 
	private Map<Integer,PduRespHandler>statusHandlersMap = new HashMap<>();

	public ResponseStatusHandlerFactory(Map<Integer, PduRespHandler> statusHandlersMap) {
		super();
		this.statusHandlersMap = statusHandlersMap;
	}
	
	public PduRespHandler getHandler(int status) {
		return statusHandlersMap.getOrDefault(status,new DefaultStatusHandler());
	}
	
	

}
