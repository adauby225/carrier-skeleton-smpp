package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.CMD_ID_SUBMIT_SM;
import static com.cloudhopper.smpp.SmppConstants.CMD_ID_UNBIND;

import java.util.HashMap;
import java.util.Map;

import com.carrier.smpp.handler.pdu.request.EsmePduRequest;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.Handlers;
import com.cloudhopper.smpp.pdu.PduResponse;

public class PduHandlersTester implements Handlers{
	private Map<Integer, RequestHandler<EsmePduRequest,PduResponse>>requestHandlers = new HashMap<>();
	private Map<Integer, ResponseHandler> responseHandlers = new HashMap<>();
	
	
	public PduHandlersTester() {
		requestHandlers.put(CMD_ID_SUBMIT_SM, new SubmitSmHandler());
		requestHandlers.put(CMD_ID_UNBIND, new UnbindHandlerTester());
	}
	@Override
	public Map<Integer, RequestHandler<EsmePduRequest,PduResponse>> getRequestHandlers() {
		
		return requestHandlers;
	}

	@Override
	public Map<Integer, ResponseHandler> getResponseHandlers() {
		// TODO Auto-generated method stub
		return responseHandlers;
	}

}
