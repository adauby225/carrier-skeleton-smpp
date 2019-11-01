package com.carrier.smpp.server;

import com.cloudhopper.smpp.SmppSessionConfiguration;

public interface BindRequestHandler {

	int handleRequest(SmppSessionConfiguration sessionConfiguration);

}
