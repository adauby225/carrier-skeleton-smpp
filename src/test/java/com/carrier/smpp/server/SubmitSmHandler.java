package com.carrier.smpp.server;

import com.carrier.smpp.handler.pdu.request.EsmePduRequest;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;

public class SubmitSmHandler implements RequestHandler<EsmePduRequest,PduResponse> {
	private static final String MESSAGE_ID = "fcc45-523kl-j8ep";
	@Override
	public PduResponse handleRequest(EsmePduRequest esmeRequest) {
		SubmitSm submitSm = (SubmitSm)esmeRequest.getRequest();
		SubmitSmResp submitSmResp = submitSm.createResponse();
		submitSmResp.setMessageId(MESSAGE_ID);
		return submitSmResp;
	}

}
