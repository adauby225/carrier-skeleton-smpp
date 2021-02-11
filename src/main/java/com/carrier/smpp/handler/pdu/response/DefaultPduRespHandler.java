package com.carrier.smpp.handler.pdu.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduResponse;

public class DefaultPduRespHandler implements ResponseHandler<PduAsyncResponse> {
	Logger logger = LogManager.getLogger(DefaultPduRespHandler.class.getName());
	@Override
	public void handleResponse(PduAsyncResponse pduAsyncResponse) {
		PduResponse response = pduAsyncResponse.getResponse();
		logger.info(response);
	}

}
