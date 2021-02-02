package com.carrier.smpp.demo.server;

import com.carrier.smpp.esme.request.EsmePduRequest;
import com.carrier.smpp.pdu.Handler.PduRequestHandler;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.Unbind;

public class UnbindHandlerExple implements PduRequestHandler<EsmePduRequest> {

	@Override
	public PduResponse handleRequest(EsmePduRequest esmeRequest) {
		Unbind unbind = (Unbind)esmeRequest.getRequest();
		return unbind.createResponse();
	}

}
