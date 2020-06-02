package com.carrier.smpp.smsc.request;

import java.util.HashMap;
import java.util.Map;



public class SmscPduRequestHandlerFactory {
	private Map<Integer, SmscPduRequestHandler>handlers = new HashMap<>();
	public SmscPduRequestHandlerFactory(Map<Integer, SmscPduRequestHandler>handlers) {
		this.handlers = handlers;
	}

	public SmscPduRequestHandler getHandler(int cmdId) {
		return handlers.getOrDefault(cmdId,new UnhandleRequest());
	}

}
