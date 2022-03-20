package com.carrier.smpp.outbound.client;

import static com.carrier.smpp.util.SmppConstantExtension.TAG_RETRY;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.handler.pdu.request.EsmeRequestHandlerFactory;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.request.SmscRequestHandlerFactory;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.request.dispatching.RequestCollections;
import com.carrier.smpp.pdu.request.dispatching.RequestManager;
import com.carrier.smpp.util.RetryCounter;
import com.carrier.smpp.util.SmppConstantExtension;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.tlv.Tlv;
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
	
	public ClientSmppSessionHandler(RequestManager reqDispatcher) {
		this.logger = LogManager.getLogger(ClientSmppSessionHandler.class);
		this.bindType = "TRANCEIVER";
		this.reqDispatcher = reqDispatcher;
		this.session = null;
		
	}

	@Override
	public void firePduRequestExpired(PduRequest pduRequest) {
		logger.info("expired request:  {}",pduRequest);
		if(reqDispatcher!=null && !(pduRequest instanceof EnquireLink)) {
			if(pduRequest.hasOptionalParameter(SmppConstantExtension.TAG_RETRY)) {
				Tlv tvlRetryCount = pduRequest.getOptionalParameter(TAG_RETRY);
				if(!RetryCounter.isMaxRetryCountReached(tvlRetryCount)) {
					tvlRetryCount = new Tlv(TAG_RETRY, RetryCounter.incrementRetryCount(tvlRetryCount));
					pduRequest.setOptionalParameter(tvlRetryCount);
					reqDispatcher.addRequest(pduRequest);
				}
			}else {
				BigInteger initialCount = BigInteger.valueOf(1);
				pduRequest.addOptionalParameter(new Tlv(TAG_RETRY, initialCount.toByteArray()));
				reqDispatcher.addRequest(pduRequest);
			}

		}

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
