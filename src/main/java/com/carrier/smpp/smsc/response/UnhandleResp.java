package com.carrier.smpp.smsc.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduResponse;

public class UnhandleResp implements SmscPduResponseHandler {
	Logger logger = LogManager.getLogger(UnhandleResp.class.getName());
	@Override
	public void handleResponse(PduAsyncResponse pduAsyncResponse) {
		PduResponse response = pduAsyncResponse.getResponse();
		logger.info(response);
	}

}
