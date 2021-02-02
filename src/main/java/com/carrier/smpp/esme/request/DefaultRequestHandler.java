package com.carrier.smpp.esme.request;

import com.carrier.smpp.pdu.Handler.PduRequestHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class DefaultRequestHandler implements PduRequestHandler<EsmePduRequest> {

	@Override
	public PduResponse handleRequest(EsmePduRequest request) {
		PduRequest pduRequest = request.getRequest();
		return pduRequest.createResponse();
	}

}
