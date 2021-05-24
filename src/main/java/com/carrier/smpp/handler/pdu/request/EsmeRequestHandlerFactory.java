package com.carrier.smpp.handler.pdu.request;

import java.util.HashMap;
import java.util.Map;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;



public class EsmeRequestHandlerFactory {
	private Map<Integer, RequestHandler<EsmePduRequest,PduResponse>>handlers = new HashMap<>();
	public EsmeRequestHandlerFactory(Map<Integer, RequestHandler<EsmePduRequest,PduResponse>>handlers) {
		this.handlers = handlers;
	}

	public RequestHandler<EsmePduRequest,PduResponse> getHandler(int cmdId) {
		return handlers.getOrDefault(cmdId,new DefaultEsmeRequestHandler());
	}

}
