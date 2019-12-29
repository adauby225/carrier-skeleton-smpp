package com.carrier.smpp.demo.outbound;

import java.util.ArrayList;
import java.util.List;

import com.carrier.smpp.demo.server.OutboundInMemoryRepository;
import com.carrier.smpp.outbound.client.CarrierSmppOutbound;
import com.carrier.smpp.outbound.client.SmppClients;

public class Outbound {

	public static void main(String[] args) {
		List<CarrierSmppOutbound>outboundClients = new ArrayList<>();
		SmppClients smppClients = SmppClients.getInstance();
		
		smppClients.startAll();
		OutboundInMemoryRepository outboundRepository = new OutboundInMemoryRepository();
		outboundClients = outboundRepository.findOutboundClients();
		for(CarrierSmppOutbound outbound: outboundClients)
			outbound.start();
		
		

	}

}
