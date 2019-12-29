package com.carrier.smpp.demo.server;

import java.util.List;

import com.carrier.smpp.outbound.client.CarrierSmppOutbound;

public class OutboundInMemoryRepository implements ConnectorRepository{

	@Override
	public List<CarrierSmppOutbound> findOutboundClients() {
		
		return null;
	}
	 
	

}
