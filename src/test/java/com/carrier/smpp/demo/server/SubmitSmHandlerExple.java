package com.carrier.smpp.demo.server;

import com.carrier.smpp.esme.request.EsmePduRequest;
import com.carrier.smpp.pdu.Handler.PduRequestHandler;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;

public class SubmitSmHandlerExple implements PduRequestHandler<EsmePduRequest> {

	@Override
	public PduResponse handleRequest(EsmePduRequest esmeRequest) {
		SubmitSm submitSm = (SubmitSm)esmeRequest.getRequest();
		return submitSm.createResponse();
	}

}
