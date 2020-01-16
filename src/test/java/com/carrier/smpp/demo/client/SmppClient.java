package com.carrier.smpp.demo.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.outbound.client.BindTypes;
import com.carrier.smpp.outbound.client.CarrierSmppBind;
import com.carrier.smpp.outbound.client.CarrierSmppConnector;
import com.carrier.smpp.outbound.client.SharedClientBootstrap;
import com.carrier.smpp.outbound.client.ConnectorConfiguration;
import com.carrier.smpp.service.BindExecutor;

public class SmppClient {
	private static final Logger logger = LogManager.getLogger(SmppClient.class);
	public static void main(String[] args) throws InterruptedException {
		
		ConnectorConfiguration settings = new ConnectorConfiguration("sysId", "passwd", "127.0.0.1", 34567);
		
		settings.setWindowSize(1);
        settings.setName("test.carrier.0");
        settings.setHost("127.0.0.1");
        
        
		BindTypes bindTypes = new BindTypes(0,1,1);
        CarrierSmppConnector connector = new CarrierSmppConnector(settings,bindTypes,BindExecutor::runBind);
        connector.connect();
        List<CarrierSmppBind>binds = connector.getBinds();
        boolean isBound=false;
        for(CarrierSmppBind bind: binds) {
        	isBound =bind.isUp();
        	logger.info("bind is bound: " + true);
        }
        
        Thread.sleep(40000);
        
        connector.disconnect();
        BindExecutor.stopAll();
        SharedClientBootstrap sharedClientBootStrap = SharedClientBootstrap.getInstance();
        sharedClientBootStrap.stopClientBootStrap();
		
		
		
	}

}
