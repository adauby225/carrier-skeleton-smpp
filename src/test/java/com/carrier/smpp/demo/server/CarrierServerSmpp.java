package com.carrier.smpp.demo.server;

import static com.carrier.smpp.util.SkeletonThreadPools.getMonitorExecutor;
import static com.carrier.smpp.util.SkeletonThreadPools.getNewCachedPool;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.server.CarrierSmppServer;
import com.carrier.smpp.server.CarrierSmppServerHandler;

public class CarrierServerSmpp {
	private static final Logger logger = LogManager.getLogger(CarrierServerSmpp.class);
	public static void main(String[] args) throws HandlerException, IOException {
		PduHandlersDemo pduHandlers = new PduHandlersDemo();
		
		
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new EsmeBindRequestHandlerExple(), new EsmeAccountRepositoryExple(),pduHandlers);
		
		CarrierSmppServer smppServer = new CarrierSmppServer(getNewCachedPool(),getMonitorExecutor()
				, new SmppSrvConfigLoaderExple(), carrierSmppServerHandler);
		smppServer.start();
		logger.info("Press any key to stop Smpp server");
		System.in.read();
		smppServer.stop();
		logger.info("Smpp server stopped.");
	}

}
