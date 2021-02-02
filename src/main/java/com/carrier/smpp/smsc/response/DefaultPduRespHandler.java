package com.carrier.smpp.smsc.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.pdu.response.Handlable;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduResponse;

public class DefaultPduRespHandler implements Handlable<PduAsyncResponse> {
	Logger logger = LogManager.getLogger(DefaultPduRespHandler.class.getName());
	@Override
	public void handle(PduAsyncResponse pduAsyncResponse) {
		PduResponse response = pduAsyncResponse.getResponse();
		logger.info(response);
	}

}
