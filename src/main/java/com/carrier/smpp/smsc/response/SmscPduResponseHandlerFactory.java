package com.carrier.smpp.smsc.response;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.pdu.response.Handlable;
import com.cloudhopper.smpp.PduAsyncResponse;


public class SmscPduResponseHandlerFactory {
	private Map<Integer, Handlable>smscPduResponseHandlers = new HashMap<>();
	
	public SmscPduResponseHandlerFactory(Map<Integer, Handlable>smscPduResponseHandlers) {
		this.smscPduResponseHandlers = smscPduResponseHandlers;
	}

	public Handlable<PduAsyncResponse> getHandler(int cmdId) {
		return smscPduResponseHandlers.getOrDefault(cmdId, new DefaultPduRespHandler());
	}

}
