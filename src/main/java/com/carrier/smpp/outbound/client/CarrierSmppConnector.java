package com.carrier.smpp.outbound.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.carrier.smpp.service.ServiceExecutor;
import com.cloudhopper.smpp.SmppBindType;

public class CarrierSmppConnector {
	
	private SmppOutboundSettings settings;
	private BindTypes bindTypes;
	private OutboundSmppBindManager bindManager;
	private PduQueue pduQueue = new PduQueue();
	private Map<Long, CarrierSmppBind>binds=new HashMap<>();
	public CarrierSmppConnector(SmppOutboundSettings settings, BindTypes bindTypes, ServiceExecutor serviceExecutor) {
		this.settings = settings;
		this.bindTypes = bindTypes;
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor);
	}
	
	public CarrierSmppConnector(SmppOutboundSettings settings, ServiceExecutor serviceExecutor) {
		this.settings = settings;
		this.bindTypes = new BindTypes();
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor);
	}

	public void connect() {
		for(int i=0;i<bindTypes.getTranceivers();i++)
			bindManager.establishBind(settings, pduQueue,SmppBindType.TRANSCEIVER);
		for(int i=0;i<bindTypes.getReceivers();i++)
			bindManager.establishBind(settings, pduQueue,SmppBindType.RECEIVER);
		for(int i=0;i<bindTypes.getTransmitters();i++)
			bindManager.establishBind(settings, pduQueue,SmppBindType.TRANSMITTER);
	}
	
	public void disconnect() {
		bindManager.unbind();
	}
	
	public List<CarrierSmppBind> getBinds() {
		return bindManager.getListOfBinds();
	}

}
