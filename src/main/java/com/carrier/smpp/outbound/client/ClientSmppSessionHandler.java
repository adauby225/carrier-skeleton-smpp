package com.carrier.smpp.outbound.client;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.carrier.smpp.handler.pdu.request.PduRequestHandlerFactory;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.Pdu;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.cloudhopper.smpp.util.SmppSessionUtil;

public class ClientSmppSessionHandler extends DefaultSmppSessionHandler {
	private final String bindType; 
	private final Logger logger ;
	private PduQueue pduQueue;
	private PduRequestHandlerFactory smscPduReqFactory;
	private ResponseHandler<PduAsyncResponse> asyncRespHandler; 
	private final SmppSession session;
	public ClientSmppSessionHandler(String bindType,Logger logger, PduQueue pduQueue
			, Map<Integer, RequestHandler> smscReqHandlers
			, ResponseHandler<PduAsyncResponse> asyncRespHandler,SmppSession session) {
		this.bindType = bindType;
		this.logger = logger;
		this.pduQueue = pduQueue;
		this.smscPduReqFactory = new PduRequestHandlerFactory(smscReqHandlers);
		this.asyncRespHandler = asyncRespHandler;
		this.session = session;
	}
	
	public ClientSmppSessionHandler(String bindType,Logger logger
			, Map<Integer, RequestHandler> smscReqHandlers
			, Map<Integer, ResponseHandler> smscResponseHandlers
			, ResponseHandler<PduAsyncResponse> asyncHandler, SmppSession session) {
		this.bindType = bindType;
		this.logger = logger;
		this.smscPduReqFactory = new PduRequestHandlerFactory(smscReqHandlers);
		this.asyncRespHandler = asyncHandler;
		this.session = session;
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
		PduResponse response = (PduResponse) reqHandler.handleRequest(pduRequest);
		logger.info("[response returned] : {}", response);
		return response;
	}

	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) { 
		PduResponse resp = pduAsyncResponse.getResponse();
		logger.info("[response received] : {}",resp);
		asyncRespHandler.handleResponse(pduAsyncResponse);
	}

	@Override
	public void fireChannelUnexpectedlyClosed() {
		logger.error(bindType +": unexpected close occurred...");
	}
	
	@Override
	public void fireUnrecoverablePduException(UnrecoverablePduException e) {
		logger.error(e);
		SmppSessionUtil.close(session);
	}
	


}
