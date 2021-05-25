package com.carrier.smpp.handler.pdu.request;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class DefaultSmscRequestHandler implements RequestHandler<PduRequest,PduResponse> {
	@Override
	public PduResponse handleRequest(PduRequest pduRequest) {
		return pduRequest.createResponse();
	}

}
