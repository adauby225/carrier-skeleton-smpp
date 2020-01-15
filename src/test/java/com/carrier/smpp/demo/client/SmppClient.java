package com.carrier.smpp.demo.client;

import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;

import com.carrier.smpp.outbound.client.CarrierSmppBind;
import com.carrier.smpp.outbound.client.OutboundSmppBindManager;
import com.carrier.smpp.outbound.client.PduQueue;
import com.carrier.smpp.outbound.client.SmppOutboundSettings;
import com.carrier.smpp.service.BindExecutor;
import com.cloudhopper.smpp.impl.DefaultSmppClient;

public class SmppClient {

	public static void main(String[] args) {
		DefaultSmppClient clientBootstrap=null;
		
		SmppOutboundSettings settings = new SmppOutboundSettings(TRANSCEIVER
				, "syst.test", "pass.test");
		
		
		settings.setWindowSize(1);
        settings.setName("test.carrier.0");
        settings.setHost("127.0.0.1");
        settings.setPort(2588);
        settings.setConnectTimeout(10000);
        settings.getLoggingOptions().setLogBytes(false);
        // to enable monitoring (request expiration)
        settings.setRequestExpiryTimeout(30000);
        settings.setWindowMonitorInterval(15000);
        settings.setCountersEnabled(true);
		
		OutboundSmppBindManager bindManager= new OutboundSmppBindManager(BindExecutor::runBind);
		CarrierSmppBind bindToSmsc = bindManager.establishBind(settings, new PduQueue());
		
		
		
	}

}
