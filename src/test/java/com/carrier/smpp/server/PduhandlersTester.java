package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.CMD_ID_SUBMIT_SM;
import static com.cloudhopper.smpp.SmppConstants.CMD_ID_UNBIND;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.esme.request.Handlers;
import com.carrier.smpp.esme.response.EsmeResponseHandler;

public class PduhandlersTester implements Handlers{
	private Map<Integer, EsmeRequestHandler>requestHandlers = new HashMap<>();
	private Map<Integer, EsmeResponseHandler> responseHandlers = new HashMap<>();
	
	
	public PduhandlersTester() {
		requestHandlers.put(CMD_ID_SUBMIT_SM, new SubmitSmHandler());
		requestHandlers.put(CMD_ID_UNBIND, new UnbindHandlerTester());
	}
	@Override
	public Map<Integer, EsmeRequestHandler> getrequestHandlers() {
		
		return requestHandlers;
	}

	@Override
	public Map<Integer, EsmeResponseHandler> getResponseHandlers() {
		// TODO Auto-generated method stub
		return responseHandlers;
	}

}
