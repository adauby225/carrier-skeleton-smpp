package com.carrier.smpp.handler.pdu.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.handler.pdu.response.DefaultPduRespHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class UnhandleRequest implements RequestHandler<EsmePduRequest,PduResponse> {
	private Logger logger = LogManager.getLogger(DefaultPduRespHandler.class.getName());
	@Override
	public PduResponse handleRequest(EsmePduRequest request) {
		PduRequest pduRequest = request.getRequest();
		return pduRequest.createResponse();
	}

}
