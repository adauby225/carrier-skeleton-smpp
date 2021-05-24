package com.carrier.smpp.handler.pdu.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.handler.pdu.response.DefaultPduRespHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class DefaultSmscRequestHandler implements RequestHandler<PduRequest,PduResponse> {
	private Logger logger = LogManager.getLogger(DefaultPduRespHandler.class.getName());
	@Override
	public PduResponse handleRequest(PduRequest pduRequest) {
		return pduRequest.createResponse();
	}

}
