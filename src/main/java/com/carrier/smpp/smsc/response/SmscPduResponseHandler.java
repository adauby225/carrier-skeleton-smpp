package com.carrier.smpp.smsc.response;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.pdu.response.Handlable;
import com.carrier.smpp.pdu.response.ResponseStatusHandlerFactory;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduResponse;

public class SmscPduResponseHandler implements Handlable<PduAsyncResponse> {
	private Logger logger = LogManager.getLogger(SmscPduResponseHandler.class);
	private final ResponseStatusHandlerFactory respStatusHandlerFactory;
	public SmscPduResponseHandler(Map<Integer, Handlable> responseStatusMap) {
		super();
		respStatusHandlerFactory = new ResponseStatusHandlerFactory(responseStatusMap);
	}

	@Override
	public void handle(PduAsyncResponse asyncResp) {
		try {
			PduResponse response = asyncResp.getResponse();
			Handlable<PduAsyncResponse> responseStatusHandler = respStatusHandlerFactory
					.getHandler(response.getCommandStatus());
			responseStatusHandler.handle(asyncResp);
		}catch(Exception e) {
			logger.error(e);
		}
		
	}
	
	
}
