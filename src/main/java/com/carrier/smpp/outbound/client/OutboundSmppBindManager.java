package com.carrier.smpp.outbound.client;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.carrier.smpp.service.ServiceExecutor;
import com.cloudhopper.smpp.SmppBindType;

public class OutboundSmppBindManager implements Connection<SmppOutboundSettings> {
	private static AtomicLong bindIds = new  AtomicLong(1);
	private ServiceExecutor serviceExecutor;
	private Map<Long, CarrierSmppBind> binds;
	public OutboundSmppBindManager(Map<Long, CarrierSmppBind> binds, ServiceExecutor serviceExecutor) {
		this.serviceExecutor = serviceExecutor;
		this.binds = binds;
	}
	
	@Override
	public void establishBind(SmppOutboundSettings settings,PduQueue pduQueue, SmppBindType bindType) {
		SharedClientBootstrap sharedClientBootStrap = SharedClientBootstrap.getInstance();
		CarrierSmppBind bind =new CarrierSmppBind(bindIds.getAndIncrement()
				,sharedClientBootStrap.getClientBootstrap(), pduQueue, settings);
		serviceExecutor.execute(bind);
		binds.put(bind.getId(), bind);
	}

	public void unbind() {
		for(CarrierSmppBind bind: binds.values()) {
			bind.unbind();
			bind.setUnbound(true);
		}
		//startSendingSignal.countDown();
	}

	public List<CarrierSmppBind> getListOfBinds() {
		return binds.values().stream().map(CarrierSmppBind::self).collect(toList());
	}


	
}
