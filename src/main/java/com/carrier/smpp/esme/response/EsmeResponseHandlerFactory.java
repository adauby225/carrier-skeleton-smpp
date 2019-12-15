package com.carrier.smpp.esme.response;

import java.util.Map;

public class EsmeResponseHandlerFactory {
	private final Map<Integer, EsmeResponseHandler> handlers;
	
	public EsmeResponseHandlerFactory(Map<Integer, EsmeResponseHandler> handlers) {
		this.handlers = handlers;
	}
	
	public EsmeResponseHandler getResponseHandler(int commandId) {
		return handlers.getOrDefault(commandId,new DefaultResponseHandler());
	}
}
