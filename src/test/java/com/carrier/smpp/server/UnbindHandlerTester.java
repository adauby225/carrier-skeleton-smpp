package com.carrier.smpp.server;

import com.carrier.smpp.handler.pdu.request.EsmePduRequest;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.Unbind;

public class UnbindHandlerTester implements RequestHandler<EsmePduRequest> {

	@Override
	public PduResponse handleRequest(EsmePduRequest emseRequest) {
		Unbind unbind = (Unbind)emseRequest.getRequest();
		return unbind.createResponse();
	}

}

