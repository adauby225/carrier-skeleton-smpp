package com.carrier.smpp.outbound.client;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.carrier.smpp.pdu.response.Handlable;
import com.carrier.smpp.smsc.request.SmscPduRequestHandler;
import com.carrier.smpp.smsc.request.SmscPduRequestHandlerFactory;
import com.carrier.smpp.smsc.response.SmscPduResponseHandlerFactory;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class ClientSmppSessionHandler extends DefaultSmppSessionHandler {
	private final String bindType; 
	private final Logger logger ;
	private PduQueue pduQueue;
	private SmscPduRequestHandlerFactory smscPduReqFactory;
	private SmscPduResponseHandlerFactory smscPduRespHandlerFactory;
	public ClientSmppSessionHandler(String bindType,Logger logger, PduQueue pduQueue
			, Map<Integer, SmscPduRequestHandler> smscReqHandlers
			, Map<Integer, Handlable> smscResponseHandlers) {
		this.bindType = bindType;
		this.logger = logger;
		this.pduQueue = pduQueue;
		this.smscPduReqFactory = new SmscPduRequestHandlerFactory(smscReqHandlers);
		this.smscPduRespHandlerFactory = new SmscPduResponseHandlerFactory(smscResponseHandlers);
	}
	
	public ClientSmppSessionHandler(String bindType,Logger logger
			, Map<Integer, SmscPduRequestHandler> smscReqHandlers
			, Map<Integer, Handlable> smscResponseHandlers) {
		this.bindType = bindType;
		this.logger = logger;
		this.smscPduReqFactory = new SmscPduRequestHandlerFactory(smscReqHandlers);
		this.smscPduRespHandlerFactory = new SmscPduResponseHandlerFactory(smscResponseHandlers);
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
		SmscPduRequestHandler reqHandler = smscPduReqFactory.getHandler(pduRequest.getCommandId());
		PduResponse response = reqHandler.handle(pduRequest);
		logger.info("[response returned] : {}", response);
		return response;
	}

	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) { 
		PduResponse resp = pduAsyncResponse.getResponse();
		logger.info("[response received] : {}",resp);
		Handlable respHandler = smscPduRespHandlerFactory.getHandler(resp.getCommandId());
		respHandler.handle(pduAsyncResponse);
	}

	@Override
	public void fireChannelUnexpectedlyClosed() {
		logger.error(bindType +": unexpected close occurred...");
	}


}
