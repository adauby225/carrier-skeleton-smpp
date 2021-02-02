package com.carrier.smpp.smsc.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.smsc.response.DefaultPduRespHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class UnhandleRequest implements SmscPduRequestHandler {
	private Logger logger = LogManager.getLogger(DefaultPduRespHandler.class.getName());
	@Override
	public PduResponse handle(PduRequest pduRequest) {
		
		return null;
	}

}
