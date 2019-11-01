package com.carrier.smpp.demo.server;

import static com.carrier.util.SkeletonExecutors.getExecutor;
import static com.carrier.util.SkeletonExecutors.getMonitorExecutor;

import java.util.ArrayList;
import java.util.List;

import com.carrier.smpp.Carrier;
import com.carrier.smpp.server.CarrierSmppEntity;
import com.carrier.smpp.server.CarrierSmppServer;
import com.carrier.smpp.server.CarrierSmppServerHandler;
import com.carrier.util.SkeletonExecutors;

public class CarrierServerSmpp {
	
	public static void main(String[] args) {
		
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler();
		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, new SmppSrvConfigLoader(), carrierSmppServerHandler);
		List<CarrierSmppEntity>carrierEntities = new ArrayList<>();
		carrierEntities.add(smppServer);
		Carrier carrier = new Carrier(carrierEntities);
		carrier.startEntities();
		
		
	}

}