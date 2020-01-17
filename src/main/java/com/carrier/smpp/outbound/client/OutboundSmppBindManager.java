package com.carrier.smpp.outbound.client;

import static com.carrier.util.Values.DEFAULT_CARRIER_REQUEST_EXPIRY_TIMEOUT;
import static com.carrier.util.Values.DEFAULT_CARRIER_WINDOW_MONITOR_TIMEOUT;
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
	private final RequestSender requestSender;
	public OutboundSmppBindManager(Map<Long, CarrierSmppBind> binds, ServiceExecutor serviceExecutor
			, RequestSender requestSender) {
		this.serviceExecutor = serviceExecutor;
		this.binds = binds;
		this.requestSender = requestSender;
		
	}
	
	@Override
	public void establishBind(ConnectorConfiguration settings,PduQueue pduQueue, SmppBindType bindType,int tps) {
		SmppSessionConfiguration config = getSessionConfig(settings, bindType);
		CarrierSmppBind bind =new CarrierSmppBind(bindIds.getAndIncrement(), pduQueue, config
				, requestSender, settings.getNpi(), settings.getTon(), tps);
		serviceExecutor.execute(bind);
		binds.put(bind.getId(), bind);
	}
	
	public SmppSessionConfiguration getSessionConfig(ConnectorConfiguration connectorConfig,SmppBindType type) {
		SmppSessionConfiguration config = new SmppSessionConfiguration(type, connectorConfig.getLogin(), connectorConfig.getPassword());
		config.setHost(connectorConfig.getRemoteHost());
		config.setPort(connectorConfig.getRemotePort());
		config.setWindowSize(connectorConfig.getWindowSize());
		config.setRequestExpiryTimeout(DEFAULT_CARRIER_REQUEST_EXPIRY_TIMEOUT);
        config.setWindowMonitorInterval(DEFAULT_CARRIER_WINDOW_MONITOR_TIMEOUT);
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

	@Override
	public void establishBind(ConnectorConfiguration t, PduQueue pduQueue, int tps) {
		
		
	}


	
}
