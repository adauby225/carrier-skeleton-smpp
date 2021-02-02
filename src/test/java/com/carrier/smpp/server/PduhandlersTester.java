package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.CMD_ID_SUBMIT_SM;
import static com.cloudhopper.smpp.SmppConstants.CMD_ID_UNBIND;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.esme.request.Handlers;
import com.carrier.smpp.esme.response.EsmeResponseHandler;
import com.carrier.smpp.pdu.Handler.PduRequestHandler;

public class PduhandlersTester implements Handlers{
	private Map<Integer, PduRequestHandler>requestHandlers = new HashMap<>();
	private Map<Integer, EsmeResponseHandler> responseHandlers = new HashMap<>();
	
	
	public PduhandlersTester() {
		requestHandlers.put(CMD_ID_SUBMIT_SM, new SubmitSmHandler());
		requestHandlers.put(CMD_ID_UNBIND, new UnbindHandlerTester());
	}
	@Override
	public Map<Integer, PduRequestHandler> getrequestHandlers() {
		
		return requestHandlers;
	}

	@Override
	public Map<Integer, EsmeResponseHandler> getResponseHandlers() {
		// TODO Auto-generated method stub
		return responseHandlers;
	}

}
