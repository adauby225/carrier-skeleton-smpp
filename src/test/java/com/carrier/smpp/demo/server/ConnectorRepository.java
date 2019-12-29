package com.carrier.smpp.demo.server;

import java.util.List;

import com.carrier.smpp.outbound.client.CarrierSmppOutbound;

public interface ConnectorRepository {
	public List<CarrierSmppOutbound> findOutboundClients();
}
