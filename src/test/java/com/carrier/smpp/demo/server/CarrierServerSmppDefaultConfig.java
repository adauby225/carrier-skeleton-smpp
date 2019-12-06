package com.carrier.smpp.demo.server;

import static com.carrier.util.SkeletonExecutors.getExecutor;
import static com.carrier.util.SkeletonExecutors.getMonitorExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carrier.smpp.Carrier;
import com.carrier.smpp.server.CarrierSmppEntity;
import com.carrier.smpp.server.CarrierSmppServer;
import com.carrier.smpp.server.CarrierSmppServerHandler;
import com.carrier.smpp.server.ConfigParameter;
import com.carrier.smpp.server.DefaultEsmeBindRequestHandler;
import com.carrier.smpp.server.DefaultPasswordParameter;
import com.carrier.smpp.server.DefaultSystemIdParameter;

public class CarrierServerSmppDefaultConfig {

	public static void main(String[] args) {
		List<ConfigParameter>parameters = Arrays.asList(new DefaultSystemIdParameter(),new DefaultPasswordParameter());
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(
				parameters,new EsmeAccountRepositoryExple());
		
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(defaultEsmeBindRequestHandler
				,new EsmeAccountRepositoryExple());
		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, carrierSmppServerHandler);
		
		List<CarrierSmppEntity>carrierEntities = new ArrayList<>();
		carrierEntities.add(smppServer);
		Carrier carrier = new Carrier(carrierEntities);
		carrier.startEntities();

	}

}
