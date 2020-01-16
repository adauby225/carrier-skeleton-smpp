package com.carrier.smpp.outbound.client;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.carrier.smpp.service.ServiceExecutor;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class OutboundSmppBindManager implements Connection<ConnectorConfiguration> {
	private static AtomicLong bindIds = new  AtomicLong(1);
	private ServiceExecutor serviceExecutor;
	private Map<Long, CarrierSmppBind> binds;
	public OutboundSmppBindManager(Map<Long, CarrierSmppBind> binds, ServiceExecutor serviceExecutor) {
		this.serviceExecutor = serviceExecutor;
		this.binds = binds;
	}
	
	@Override
	public void establishBind(ConnectorConfiguration settings,PduQueue pduQueue, SmppBindType bindType) {
		SharedClientBootstrap sharedClientBootStrap = SharedClientBootstrap.getInstance();
		SmppSessionConfiguration config = getSessionConfig(settings, bindType);
		CarrierSmppBind bind =new CarrierSmppBind(bindIds.getAndIncrement()
				,sharedClientBootStrap.getClientBootstrap(), pduQueue, config);
		serviceExecutor.execute(bind);
		binds.put(bind.getId(), bind);
	}
	
	public SmppSessionConfiguration getSessionConfig(ConnectorConfiguration connectorConfig,SmppBindType type) {
		SmppSessionConfiguration config = new SmppSessionConfiguration(type, connectorConfig.getLogin(), connectorConfig.getPassword());
		config.setHost(connectorConfig.getRemoteHost());
		config.setPort(connectorConfig.getRemotePort());
		config.setWindowSize(connectorConfig.getWindowSize());
		if(!connectorConfig.isHostEmpty())
			config.setLocalAddress(connectorConfig.getHost());
		return config;
	}

	public void unbind() {
		for(CarrierSmppBind bind: binds.values()) {
			bind.unbind();
			bind.setUnbound(true);
		}
	}

	public List<CarrierSmppBind> getListOfBinds() {
		return binds.values().stream().map(CarrierSmppBind::self).collect(toList());
	}


	
}
