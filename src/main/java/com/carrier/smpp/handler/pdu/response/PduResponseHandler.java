package com.carrier.smpp.handler.pdu.response;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduResponse;

public class PduResponseHandler implements ResponseHandler<PduAsyncResponse> {
	private Logger logger = LogManager.getLogger(PduResponseHandler.class);
	private final ResponseStatusHandlerFactory respStatusHandlerFactory;
	public PduResponseHandler(Map<Integer, ResponseHandler> responseStatusMap) {
		super();
		respStatusHandlerFactory = new ResponseStatusHandlerFactory(responseStatusMap);
	}

	@Override
	public void handleResponse(PduAsyncResponse asyncResp) {
		try {
			PduResponse response = asyncResp.getResponse();
			ResponseHandler<PduAsyncResponse> responseStatusHandler = respStatusHandlerFactory
					.getHandler(response.getCommandStatus());
			responseStatusHandler.handleResponse(asyncResp);
		}catch(Exception e) {
			logger.error(e);
		}
		
	}
	
	
}
