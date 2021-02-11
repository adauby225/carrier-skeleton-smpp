package com.carrier.smpp.demo.server;

import com.carrier.smpp.handler.pdu.request.EsmePduRequest;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.Unbind;

public class UnbindHandlerExple implements RequestHandler<EsmePduRequest> {

	@Override
	public PduResponse handleRequest(EsmePduRequest esmeRequest) {
		Unbind unbind = (Unbind)esmeRequest.getRequest();
		return unbind.createResponse();
	}

}
