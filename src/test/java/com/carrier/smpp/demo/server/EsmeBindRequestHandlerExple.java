package com.carrier.smpp.demo.server;

import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class EsmeBindRequestHandlerExple implements RequestHandler<SmppSessionConfiguration,Integer> {

	@Override
	public Integer handleRequest(SmppSessionConfiguration sessionConfiguration) {
		return SmppConstants.STATUS_OK;
	}

}
