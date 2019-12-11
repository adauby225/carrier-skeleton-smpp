package com.carrier.smpp.demo.server;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.server.EsmeSmppSession;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;

public class SubmitSmHandlerExple implements EsmeRequestHandler {

	@Override
	public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
		SubmitSm submitSm = (SubmitSm)pduRequest;
		return submitSm.createResponse();
	}

}
