package com.carrier.smpp.demo.server;

import static com.carrier.smpp.util.SkeletonThreadPools.getNewCachedPool;
import static com.carrier.smpp.util.SkeletonThreadPools.getMonitorExecutor;

import com.carrier.smpp.server.CarrierSmppServer;
import com.carrier.smpp.server.CarrierSmppServerHandler;

public class CarrierServerSmpp {
	
	public static void main(String[] args) throws HandlerException {
		PduHandlersDemo pduHandlers = new PduHandlersDemo();
		
		
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new EsmeBindRequestHandlerExple(), new EsmeAccountRepositoryExple(),pduHandlers);
		
		CarrierSmppServer smppServer = new CarrierSmppServer(getNewCachedPool(),getMonitorExecutor()
				, new SmppSrvConfigLoaderExple(), carrierSmppServerHandler);
		smppServer.start();
		smppServer.stop();
	}

}
