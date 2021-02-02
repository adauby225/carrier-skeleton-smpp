package com.carrier.smpp.pdu.Handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;

public class DefaultStatusHandler implements PduRespHandler<PduAsyncResponse> {
	private Logger logger = LogManager.getLogger(DefaultStatusHandler.class);
	@Override
	public void handle(PduAsyncResponse pduAsyncResponse) {
		logger.info("Response received: {}",pduAsyncResponse.getResponse());
	}

}
