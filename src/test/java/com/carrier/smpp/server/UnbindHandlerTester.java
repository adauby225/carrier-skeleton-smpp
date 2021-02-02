package com.carrier.smpp.server;

import com.carrier.smpp.esme.request.EsmePduRequest;
import com.carrier.smpp.pdu.Handler.PduRequestHandler;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.Unbind;

public class UnbindHandlerTester implements PduRequestHandler<EsmePduRequest> {

	@Override
	public PduResponse handleRequest(EsmePduRequest emseRequest) {
		Unbind unbind = (Unbind)emseRequest.getRequest();
		return unbind.createResponse();
	}

}

