package com.carrier.smpp.smsc.response;

import java.util.HashMap;
import java.util.Map;


public class SmscPduResponseHandlerFactory {
	private Map<Integer, SmscPduResponseHandler>responseHandlers = new HashMap<>();
	
	public SmscPduResponseHandlerFactory(Map<Integer, SmscPduResponseHandler>responseHandlers) {
		this.responseHandlers = responseHandlers;
	}

	public SmscPduResponseHandler getHandler(int cmdId) {
		return responseHandlers.get(cmdId);
	}

}
