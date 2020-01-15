package com.carrier.smpp.outbound.client;

import java.util.concurrent.atomic.AtomicLong;

import com.carrier.smpp.service.ServiceExecutor;

public class OutboundSmppBindManager implements Connection<SmppOutboundSettings, CarrierSmppBind> {
	private static AtomicLong bindIds = new  AtomicLong(1);
	private ServiceExecutor serviceExecutor;
	
	public OutboundSmppBindManager(ServiceExecutor serviceExecutor) {
		this.serviceExecutor = serviceExecutor;
	}
	
	@Override
	public CarrierSmppBind establishBind(SmppOutboundSettings settings,PduQueue pduQueue) {
		SharedClientBootstrap sharedClientBootStrap = SharedClientBootstrap.getInstance();
		CarrierSmppBind bind =new CarrierSmppBind(bindIds.getAndIncrement()
				,sharedClientBootStrap.getClientBootstrap(), pduQueue, settings);
		serviceExecutor.execute(bind);
		return bind;
	}


	
}
