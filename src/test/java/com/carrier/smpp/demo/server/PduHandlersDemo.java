package com.carrier.smpp.demo.server;

import static com.cloudhopper.smpp.SmppConstants.CMD_ID_SUBMIT_SM;
import static com.cloudhopper.smpp.SmppConstants.CMD_ID_UNBIND;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.Handlers;

public class PduHandlersDemo implements Handlers {
	private Map<Integer, RequestHandler>requestHandlers = new HashMap<>();
	private Map<Integer, ResponseHandler> responseHandlers = new HashMap<>();
	
	public PduHandlersDemo() {
		requestHandlers.put(CMD_ID_SUBMIT_SM, new SubmitSmHandlerExple());
		requestHandlers.put(CMD_ID_UNBIND, new UnbindHandlerExple());
	}
	@Override
	public Map<Integer, RequestHandler> getRequestHandlers() {
		return requestHandlers;
	}

	@Override
	public Map<Integer, ResponseHandler> getResponseHandlers() {
		return responseHandlers;
	}

}
