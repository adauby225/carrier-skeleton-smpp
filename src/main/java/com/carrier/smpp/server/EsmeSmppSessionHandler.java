package com.carrier.smpp.server;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.handler.pdu.request.EsmePduRequest;
import com.carrier.smpp.handler.pdu.request.EsmeRequestHandlerFactory;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.handler.pdu.response.PduResponseHandlerFactory;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class EsmeSmppSessionHandler extends DefaultSmppSessionHandler {
	private final Logger logger;
	private final Long sessionId;
	private EsmeSmppSession esmeSmppSession;
	private final EsmeRequestHandlerFactory esmeRequestHandlerFactory;
	private final PduResponseHandlerFactory esmeResponseHandlerFactory;
	public EsmeSmppSessionHandler(Long sessionId, EsmeSmppSession esmeSmppSession
			,Map<Integer, RequestHandler<EsmePduRequest,PduResponse>>handlers
			, Map<Integer, ResponseHandler> responseHandlers) {
		this.sessionId = sessionId;
		this.esmeSmppSession = esmeSmppSession;
		this.esmeRequestHandlerFactory = new EsmeRequestHandlerFactory(handlers);
		this.esmeResponseHandlerFactory = new PduResponseHandlerFactory(responseHandlers);
		this.logger = LogManager.getLogger(esmeSmppSession.getAccountName());
	}
	public Long getSessionId() {
		return sessionId;
	}
	
	@Override
	public PduResponse firePduRequestReceived(PduRequest pduRequest) {
		logger.info("[request received] : {}",pduRequest);
		RequestHandler esmeRequestHandler = esmeRequestHandlerFactory
				.getHandler(pduRequest.getCommandId());
		PduResponse response = (PduResponse) esmeRequestHandler.handleRequest(new EsmePduRequest(pduRequest,esmeSmppSession));
		logger.info("[response returned] : {}",response);
		return response;
		
	}
	
	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
		PduResponse response = pduAsyncResponse.getResponse();
		logger.info("[response received] : {}",response);
		ResponseHandler responseHandler = esmeResponseHandlerFactory.getHandler(response.getCommandId());
		responseHandler.handleResponse(pduAsyncResponse);
	}
	
	
	
	
	
	

}
