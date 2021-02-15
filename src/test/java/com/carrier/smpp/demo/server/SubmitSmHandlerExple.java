package com.carrier.smpp.demo.server;

import com.carrier.smpp.handler.pdu.request.EsmePduRequest;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;

public class SubmitSmHandlerExple implements RequestHandler<EsmePduRequest,PduResponse> {

	@Override
	public PduResponse handleRequest(EsmePduRequest esmeRequest) {
		SubmitSm submitSm = (SubmitSm)esmeRequest.getRequest();
		return submitSm.createResponse();
	}

}
