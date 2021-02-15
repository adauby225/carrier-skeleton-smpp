package com.carrier.smpp.handler.pdu.response;

import java.util.HashMap;
import java.util.Map;

import com.cloudhopper.smpp.PduAsyncResponse;


public class PduResponseHandlerFactory {
	private Map<Integer, ResponseHandler>responseHandlers = new HashMap<>();
	
	public PduResponseHandlerFactory(Map<Integer, ResponseHandler>responseHandlers) {
		this.responseHandlers = responseHandlers;
	}

	public ResponseHandler<PduAsyncResponse> getHandler(int cmdId) {
		return responseHandlers.getOrDefault(cmdId, new DefaultPduRespHandler());
	}

}
