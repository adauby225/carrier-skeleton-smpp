package com.carrier.smpp.handler.pdu.request;

import java.util.Map;

public class EsmeRequestHandlerFactory {
	private final Map<Integer, RequestHandler> handlers;
	public EsmeRequestHandlerFactory(Map<Integer, RequestHandler> handlers) {
		this.handlers = handlers;
	}

	public RequestHandler getRequestHandler(int commandId) {
		return handlers.getOrDefault(commandId, new DefaultRequestHandler());
	}

}
