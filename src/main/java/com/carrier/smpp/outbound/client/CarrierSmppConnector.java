package com.carrier.smpp.outbound.client;

import static com.cloudhopper.smpp.SmppBindType.RECEIVER;
import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppBindType.TRANSMITTER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.carrier.smpp.service.ServiceExecutor;
import com.cloudhopper.smpp.pdu.PduRequest;

public class CarrierSmppConnector {
	
	private ConnectorConfiguration connectorConfig;
	private BindTypes bindTypes;
	private OutboundSmppBindManager bindManager;
	private PduQueue pduQueue = new PduQueue();
	private Map<Long, CarrierSmppBind>binds=new HashMap<>();
	private final MaxRequestPerSecond maxReqPerSecond;
	
	public CarrierSmppConnector(ConnectorConfiguration connectorConfig, BindTypes bindTypes
			, ServiceExecutor serviceExecutor, RequestSender requestSender,MaxRequestPerSecond maxReqPerSecond) {
		this.connectorConfig = connectorConfig;
		this.bindTypes = bindTypes;
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor,requestSender);
		this.maxReqPerSecond = maxReqPerSecond;
	}
	
	public CarrierSmppConnector(ConnectorConfiguration connectorConfig, ServiceExecutor serviceExecutor
			,RequestSender requestSender,MaxRequestPerSecond maxReqPerSecond) {
		this.connectorConfig = connectorConfig;
		this.bindTypes = new BindTypes();
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor,requestSender);
		this.maxReqPerSecond = maxReqPerSecond;
	}

	public void connect() {
		int tpsByBind =maxReqPerSecond.calculateTpsByBind(bindTypes, connectorConfig.getThroughput());
		for(int i=0;i<bindTypes.getTranceivers();i++)
			bindManager.establishBind(connectorConfig, pduQueue,TRANSCEIVER,tpsByBind);
		for(int i=0;i<bindTypes.getReceivers();i++)
			bindManager.establishBind(connectorConfig, pduQueue,RECEIVER,tpsByBind);
		for(int i=0;i<bindTypes.getTransmitters();i++)
			bindManager.establishBind(connectorConfig, pduQueue,TRANSMITTER,tpsByBind);
	}
	
	public void disconnect() {
		bindManager.unbind();
	}
	
	public List<CarrierSmppBind> getBinds() {
		return bindManager.getListOfBinds();
	}
	
	public void addRequestFirst(PduRequest pduRequest) {
		pduQueue.addRequestFirst(pduRequest);
	}
	
	public void addRequestLast(PduRequest pduRequest) {
		pduQueue.addRequestLast(pduRequest);
	}

}
