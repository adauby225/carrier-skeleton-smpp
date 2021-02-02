package com.carrier.smpp.smsc.response;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.pdu.Handler.PduRespHandler;
import com.cloudhopper.smpp.PduAsyncResponse;


public class SmscPduResponseHandlerFactory {
	private Map<Integer, PduRespHandler>smscPduResponseHandlers = new HashMap<>();
	
	public SmscPduResponseHandlerFactory(Map<Integer, PduRespHandler>smscPduResponseHandlers) {
		this.smscPduResponseHandlers = smscPduResponseHandlers;
	}

	public PduRespHandler<PduAsyncResponse> getHandler(int cmdId) {
		return smscPduResponseHandlers.getOrDefault(cmdId, new DefaultPduRespHandler());
	}

}
