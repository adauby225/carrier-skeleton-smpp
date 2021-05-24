package com.carrier.smpp.demo.server;

import static com.cloudhopper.smpp.SmppConstants.CMD_ID_SUBMIT_SM;
import static com.cloudhopper.smpp.SmppConstants.CMD_ID_UNBIND;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.handler.pdu.request.EsmePduRequest;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.Handlers;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class PduHandlersDemo implements Handlers {
	private Map<Integer, RequestHandler<EsmePduRequest, PduResponse>>requestHandlers = new HashMap<>();
	private Map<Integer, ResponseHandler> responseHandlers = new HashMap<>();
	
	public PduHandlersDemo() {
		requestHandlers.put(CMD_ID_SUBMIT_SM, new SubmitSmHandlerExple());
		requestHandlers.put(CMD_ID_UNBIND, new UnbindHandlerExple());
	}
	@Override
	public Map<Integer, RequestHandler<EsmePduRequest, PduResponse>> getRequestHandlers() {
		return requestHandlers;
	}

	@Override
	public Map<Integer, ResponseHandler> getResponseHandlers() {
		return responseHandlers;
	}

}
