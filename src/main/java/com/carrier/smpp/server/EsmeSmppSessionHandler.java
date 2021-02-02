package com.carrier.smpp.server;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.esme.request.EsmePduRequest;
import com.carrier.smpp.esme.request.EsmeRequestHandlerFactory;
import com.carrier.smpp.esme.response.EsmeResponseHandler;
import com.carrier.smpp.esme.response.EsmeResponseHandlerFactory;
import com.carrier.smpp.pdu.Handler.PduRequestHandler;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class EsmeSmppSessionHandler extends DefaultSmppSessionHandler {
	private final Logger logger;
	private final Long sessionId;
	private EsmeSmppSession esmeSmppSession;
	private final EsmeRequestHandlerFactory esmeRequestHandlerFactory;
	private final EsmeResponseHandlerFactory esmeResponseHandlerFactory;
	public EsmeSmppSessionHandler(Long sessionId, EsmeSmppSession esmeSmppSession
			,Map<Integer, PduRequestHandler>handlers
			, Map<Integer, EsmeResponseHandler> responseHandlers) {
		this.sessionId = sessionId;
		this.esmeSmppSession = esmeSmppSession;
		this.esmeRequestHandlerFactory = new EsmeRequestHandlerFactory(handlers);
		this.esmeResponseHandlerFactory = new EsmeResponseHandlerFactory(responseHandlers);
		this.logger = LogManager.getLogger(esmeSmppSession.getAccountName());
	}
	public Long getSessionId() {
		return sessionId;
	}
	
	@Override
	public PduResponse firePduRequestReceived(PduRequest pduRequest) {
		logger.info("[request received] : {}",pduRequest);
		PduRequestHandler<EsmePduRequest> esmeRequestHandler = esmeRequestHandlerFactory
				.getRequestHandler(pduRequest.getCommandId());
		PduResponse response = esmeRequestHandler.handleRequest(new EsmePduRequest(pduRequest,esmeSmppSession));
		logger.info("[response returned] : {}",response);
		return response;
		
	}
	
	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
		PduResponse response = pduAsyncResponse.getResponse();
		logger.info("[response received] : {}",response);
		EsmeResponseHandler responseHandler = esmeResponseHandlerFactory.getResponseHandler(response.getCommandId());
		responseHandler.handleResponse(pduAsyncResponse);
	}
	
	
	
	
	
	

}
