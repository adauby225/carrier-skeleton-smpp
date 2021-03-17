package com.carrier.smpp.outbound.client;

import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class ClientSmppSessionHandlerTest {
	private final int PORT = 5785;
	private OutboundSmppBindManager SmppBindManager = new OutboundSmppBindManager(null, null, null, null, null);
	private ConnectorConfiguration config = new ConnectorConfiguration("sysId", "pass", "localhost",PORT);
	SmppSessionConfiguration session = SmppBindManager.getSessionConfig(config, SmppBindType.TRANSCEIVER);
	
}
