package com.carrier.smpp.server;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.Unbind;

public class UnbindHandlerTester implements EsmeRequestHandler {

	@Override
	public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
		Unbind unbind = (Unbind)pduRequest;
		return unbind.createResponse();
	}

}

