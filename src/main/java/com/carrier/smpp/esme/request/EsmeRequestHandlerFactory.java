package com.carrier.smpp.esme.request;

import java.util.Map;

public class EsmeRequestHandlerFactory {
	private final Map<Integer, EsmeRequestHandler> handlers;
	public EsmeRequestHandlerFactory(Map<Integer, EsmeRequestHandler> handlers) {
		this.handlers = handlers;
	}

	public EsmeRequestHandler getRequestHandler(int commandId) {
		return handlers.getOrDefault(commandId, new DefaultRequestHandler());
	}

}
