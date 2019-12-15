package com.carrier.smpp.esme.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduResponse;

public class DefaultResponseHandler implements EsmeResponseHandler {
	Logger logger = LogManager.getLogger(DefaultResponseHandler.class);
	@Override
	public void handleResponse(PduAsyncResponse pduAsyncResponse) {
		PduResponse response =  pduAsyncResponse.getResponse();
		logger.info(response);
	}

}
