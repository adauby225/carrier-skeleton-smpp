package com.carrier.smpp.demo.server;

import static com.cloudhopper.smpp.SmppConstants.CMD_ID_SUBMIT_SM;
import static com.cloudhopper.smpp.SmppConstants.CMD_ID_UNBIND;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.esme.request.Handlers;
import com.carrier.smpp.esme.response.EsmeResponseHandler;

public class PduHandlersDemo implements Handlers {
	private Map<Integer, EsmeRequestHandler>requestHandlers = new HashMap<>();
	private Map<Integer, EsmeResponseHandler> responseHandlers = new HashMap<>();
	
	public PduHandlersDemo() {
		requestHandlers.put(CMD_ID_SUBMIT_SM, new SubmitSmHandlerExple());
		requestHandlers.put(CMD_ID_UNBIND, new UnbindHandlerExple());
	}
	@Override
	public Map<Integer, EsmeRequestHandler> getrequestHandlers() {
		return requestHandlers;
	}

	@Override
	public Map<Integer, EsmeResponseHandler> getResponseHandlers() {
		return responseHandlers;
	}

}
