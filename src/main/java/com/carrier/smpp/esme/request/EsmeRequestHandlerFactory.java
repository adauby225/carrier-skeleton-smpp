package com.carrier.smpp.esme.request;

import java.util.Map;

import com.carrier.smpp.pdu.Handler.PduRequestHandler;

public class EsmeRequestHandlerFactory {
	private final Map<Integer, PduRequestHandler> handlers;
	public EsmeRequestHandlerFactory(Map<Integer, PduRequestHandler> handlers) {
		this.handlers = handlers;
	}

	public PduRequestHandler getRequestHandler(int commandId) {
		return handlers.getOrDefault(commandId, new DefaultRequestHandler());
	}

}
