package com.carrier.smpp.server;

import java.util.Map;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.esme.request.EsmeRequestHandlerFactory;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class EsmeSmppSessionHandler extends DefaultSmppSessionHandler {
	private final Long sessionId;
	private EsmeSmppSession esmeSmppSession;
	private final EsmeRequestHandlerFactory esmeRequestHandlerFactory;
	public EsmeSmppSessionHandler(Long sessionId, EsmeSmppSession esmeSmppSession,Map<Integer, EsmeRequestHandler>handlers) {
		this.sessionId = sessionId;
		this.esmeSmppSession = esmeSmppSession;
		this.esmeRequestHandlerFactory = new EsmeRequestHandlerFactory(handlers);
	}
	public Long getSessionId() {
		return sessionId;
	}
	
	@Override
	public PduResponse firePduRequestReceived(PduRequest pduRequest) {
		EsmeRequestHandler esmeRequestHandler = esmeRequestHandlerFactory.getRequestHandler(pduRequest.getCommandId());
		return esmeRequestHandler.handleRequest(pduRequest,esmeSmppSession);
		
	}
	
	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
		
	}
	
	
	
	
	
	

}
