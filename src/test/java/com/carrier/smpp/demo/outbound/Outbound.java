package com.carrier.smpp.demo.outbound;

import java.util.ArrayList;
import java.util.List;

import com.carrier.smpp.demo.server.OutboundInMemoryRepository;
import com.carrier.smpp.outbound.client.SmppClients;
import com.carrier.smpp.server.CarrierSmppEntity;

public class Outbound {

	public static void main(String[] args) {
		List<CarrierSmppEntity>entities = new ArrayList<>();
		SmppClients smppClients = SmppClients.getInstance();
		
		smppClients.startAll();
		OutboundInMemoryRepository outboundRepository = new OutboundInMemoryRepository();
		entities = outboundRepository.findConnectors();
		for(CarrierSmppEntity entity: entities) {
			entity.start();
		}
		

	}

}
