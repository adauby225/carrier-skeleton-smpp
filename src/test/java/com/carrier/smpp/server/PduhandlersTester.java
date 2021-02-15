package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.CMD_ID_SUBMIT_SM;
import static com.cloudhopper.smpp.SmppConstants.CMD_ID_UNBIND;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.Handlers;

public class PduhandlersTester implements Handlers{
	private Map<Integer, RequestHandler>requestHandlers = new HashMap<>();
	private Map<Integer, ResponseHandler> responseHandlers = new HashMap<>();
	
	
	public PduhandlersTester() {
		requestHandlers.put(CMD_ID_SUBMIT_SM, new SubmitSmHandler());
		requestHandlers.put(CMD_ID_UNBIND, new UnbindHandlerTester());
	}
	@Override
	public Map<Integer, RequestHandler> getrequestHandlers() {
		
		return requestHandlers;
	}

	@Override
	public Map<Integer, ResponseHandler> getResponseHandlers() {
		// TODO Auto-generated method stub
		return responseHandlers;
	}

}
