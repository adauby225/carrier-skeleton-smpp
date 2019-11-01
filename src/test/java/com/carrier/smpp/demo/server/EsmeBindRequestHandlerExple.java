package com.carrier.smpp.demo.server;

import com.carrier.smpp.server.BindRequestHandler;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class EsmeBindRequestHandlerExple implements BindRequestHandler {

	@Override
	public int handleRequest(SmppSessionConfiguration sessionConfiguration) {
		return SmppConstants.STATUS_OK;
	}

}
