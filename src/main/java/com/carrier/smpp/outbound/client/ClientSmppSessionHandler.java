package com.carrier.smpp.outbound.client;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.request.PduRequestHandlerFactory;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.handler.pdu.response.PduResponseHandlerFactory;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class ClientSmppSessionHandler extends DefaultSmppSessionHandler {
	private final String bindType; 
	private final Logger logger ;
	private PduQueue pduQueue;
	private PduRequestHandlerFactory smscPduReqFactory;
	private PduResponseHandlerFactory smscPduRespHandlerFactory;
	public ClientSmppSessionHandler(String bindType,Logger logger, PduQueue pduQueue
			, Map<Integer, RequestHandler> smscReqHandlers
			, Map<Integer, ResponseHandler> smscResponseHandlers) {
		this.bindType = bindType;
		this.logger = logger;
		this.pduQueue = pduQueue;
		this.smscPduReqFactory = new PduRequestHandlerFactory(smscReqHandlers);
		this.smscPduRespHandlerFactory = new PduResponseHandlerFactory(smscResponseHandlers);
	}
	
	public ClientSmppSessionHandler(String bindType,Logger logger
			, Map<Integer, RequestHandler> smscReqHandlers
			, Map<Integer, ResponseHandler> smscResponseHandlers) {
		this.bindType = bindType;
		this.logger = logger;
		this.smscPduReqFactory = new PduRequestHandlerFactory(smscReqHandlers);
		this.smscPduRespHandlerFactory = new PduResponseHandlerFactory(smscResponseHandlers);
	}

	@Override
	public void firePduRequestExpired(PduRequest pduRequest) {
		logger.info("expired request:  {}",pduRequest);
		if(pduQueue!=null && !(pduRequest instanceof EnquireLink))
			pduQueue.addRequestFirst(pduRequest);

	}

	@Override
	public PduResponse firePduRequestReceived(PduRequest pduRequest) {
		logger.info("[request send] : {}",pduRequest);
		RequestHandler reqHandler = smscPduReqFactory.getHandler(pduRequest.getCommandId());
		PduResponse response = reqHandler.handleRequest(pduRequest);
		logger.info("[response returned] : {}", response);
		return response;
	}

	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) { 
		PduResponse resp = pduAsyncResponse.getResponse();
		logger.info("[response received] : {}",resp);
		ResponseHandler respHandler = smscPduRespHandlerFactory.getHandler(resp.getCommandId());
		respHandler.handleResponse(pduAsyncResponse);
	}

	@Override
	public void fireChannelUnexpectedlyClosed() {
		logger.error(bindType +": unexpected close occurred...");
	}


}
