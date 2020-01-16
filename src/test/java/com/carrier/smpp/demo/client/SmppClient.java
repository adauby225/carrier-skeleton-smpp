package com.carrier.smpp.demo.client;

import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.outbound.client.BindTypes;
import com.carrier.smpp.outbound.client.CarrierSmppBind;
import com.carrier.smpp.outbound.client.OutboundSmppBindManager;
import com.carrier.smpp.outbound.client.PduQueue;
import com.carrier.smpp.outbound.client.SharedClientBootstrap;
import com.carrier.smpp.outbound.client.SmppOutboundSettings;
import com.carrier.smpp.outbound.client.CarrierSmppConnector;
import com.carrier.smpp.service.BindExecutor;
import com.cloudhopper.smpp.impl.DefaultSmppClient;

public class SmppClient {
	private static final Logger logger = LogManager.getLogger(SmppClient.class);
	public static void main(String[] args) throws InterruptedException {
		
		SmppOutboundSettings settings = new SmppOutboundSettings("syst.test", "pass.test");
		
		settings.setWindowSize(1);
        settings.setName("test.carrier.0");
        settings.setHost("127.0.0.1");
        settings.setPort(34567);
        settings.setConnectTimeout(10000);
        settings.getLoggingOptions().setLogBytes(false);
        // to enable monitoring (request expiration)
        settings.setRequestExpiryTimeout(30000);
        settings.setWindowMonitorInterval(15000);
        settings.setCountersEnabled(true);
		BindTypes bindTypes = new BindTypes(0,1,1);
        CarrierSmppConnector connector = new CarrierSmppConnector(settings,bindTypes,BindExecutor::runBind);
        connector.connect();
        List<CarrierSmppBind>binds = connector.getBinds();
        boolean isBound=false;
        for(CarrierSmppBind bind: binds) {
        	isBound =bind.isUp();
        	logger.info("bind is bound: " + true);
        }
        
        Thread.sleep(10000);
        
        connector.disconnect();
        BindExecutor.stopAll();
        SharedClientBootstrap sharedClientBootStrap = SharedClientBootstrap.getInstance();
        sharedClientBootStrap.stopClientBootStrap();
		
		
		
	}

}
