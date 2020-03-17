package com.carrier.smpp.server;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;

public class SubmitSmHandlerTester implements EsmeRequestHandler {
	private static final String MESSAGE_ID = "fcc45-523kl-j8ep";
	@Override
	public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
		SubmitSm submitSm = (SubmitSm)pduRequest;
		SubmitSmResp submitSmResp = submitSm.createResponse();
		submitSmResp.setMessageId(MESSAGE_ID);
		return submitSmResp;
	}

}
