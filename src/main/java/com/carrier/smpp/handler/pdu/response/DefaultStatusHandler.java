package com.carrier.smpp.handler.pdu.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;

public class DefaultStatusHandler implements ResponseHandler<PduAsyncResponse> {
	private Logger logger = LogManager.getLogger(DefaultStatusHandler.class);
	@Override
	public void handleResponse(PduAsyncResponse pduAsyncResponse) {
		logger.info("Response received: {}",pduAsyncResponse.getResponse());
	}

}
