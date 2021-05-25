package com.carrier.smpp.handler.pdu.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;

public class DefaultPduRespHandler implements ResponseHandler<PduAsyncResponse> {
	Logger logger = LogManager.getLogger(DefaultPduRespHandler.class.getName());
	@Override
	public void handleResponse(PduAsyncResponse pduAsyncResponse) {
		logger.info("Handling pdu response: {}",pduAsyncResponse.getResponse());
	}

}
