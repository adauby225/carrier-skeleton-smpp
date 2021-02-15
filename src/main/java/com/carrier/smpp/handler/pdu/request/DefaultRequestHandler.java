package com.carrier.smpp.handler.pdu.request;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class DefaultRequestHandler implements RequestHandler<EsmePduRequest,PduResponse> {

	@Override
	public PduResponse handleRequest(EsmePduRequest request) {
		PduRequest pduRequest = request.getRequest();
		return pduRequest.createResponse();
	}

}
