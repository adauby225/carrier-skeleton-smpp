package com.carrier.smpp.outbound.client;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.carrier.smpp.handler.pdu.request.EsmeRequestHandlerFactory;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.request.SmscRequestHandlerFactory;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.request.dispatching.RequestManager;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.cloudhopper.smpp.util.SmppSessionUtil;

public class ClientSmppSessionHandler extends DefaultSmppSessionHandler {
	private final String bindType; 
	private final Logger logger ;
	private RequestManager reqDispatcher;
	private SmscRequestHandlerFactory smscPduReqFactory;
	private ResponseHandler<PduAsyncResponse> asyncRespHandler; 
	private final SmppSession session;
	public ClientSmppSessionHandler(String bindType,Logger logger, RequestManager reqDispatcher
			, Map<Integer, RequestHandler<PduRequest,PduResponse>> smscReqHandlers
			, ResponseHandler<PduAsyncResponse> asyncRespHandler,SmppSession session) {
		this.bindType = bindType;
		this.logger = logger;
		this.reqDispatcher = reqDispatcher;
		this.smscPduReqFactory = new SmscRequestHandlerFactory(smscReqHandlers);
		this.asyncRespHandler = asyncRespHandler;
		this.session = session;
	}
	
	public ClientSmppSessionHandler(String bindType,Logger logger
			, Map<Integer, RequestHandler<PduRequest, PduResponse>> smscReqHandlers
			, Map<Integer, ResponseHandler> smscResponseHandlers
			, ResponseHandler<PduAsyncResponse> asyncHandler, SmppSession session) {
		this.bindType = bindType;
		this.logger = logger;
		this.smscPduReqFactory = new SmscRequestHandlerFactory(smscReqHandlers);
		this.asyncRespHandler = asyncHandler;
		this.session = session;
	}

	@Override
	public void firePduRequestExpired(PduRequest pduRequest) {
		logger.info("expired request:  {}",pduRequest);
		if(reqDispatcher!=null && !(pduRequest instanceof EnquireLink))
			reqDispatcher.addRequest(pduRequest);

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
