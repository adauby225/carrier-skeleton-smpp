package com.carrier.smpp.outbound.client;

import static com.cloudhopper.smpp.SmppBindType.RECEIVER;
import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppBindType.TRANSMITTER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.carrier.smpp.service.ServiceExecutor;
import com.carrier.smpp.smsc.request.SmscPduRequestHandler;
import com.carrier.smpp.smsc.response.SmscPduResponseHandler;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.pdu.PduRequest;

public class CarrierSmppConnector {
	
	private ConnectorConfiguration connectorConfig;
	private BindTypes bindTypes;
	private OutboundSmppBindManager bindManager;
	private PduQueue pduQueue = new PduQueue();
	private Map<Long, CarrierSmppBind>binds=new HashMap<>();
	private final MaxRequestPerSecond maxReqPerSecond;
	
	
	public CarrierSmppConnector(ConnectorConfiguration connectorConfig, ServiceExecutor serviceExecutor
			,RequestSender requestSender,RequestSender enquireLinkSender,MaxRequestPerSecond maxReqPerSecond
			,Map<Integer, SmscPduRequestHandler>smscReqHandlers,Map<Integer, SmscPduResponseHandler>smscresponseHandlers) {
		this.connectorConfig = connectorConfig;
		this.bindTypes = new BindTypes();
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor,requestSender,enquireLinkSender
				,smscReqHandlers,smscresponseHandlers);
		this.maxReqPerSecond = maxReqPerSecond;
	}

	public void connect() {
		int tpsByBind =maxReqPerSecond.calculateTpsByBind(bindTypes, connectorConfig.getThroughput());
		createNewBind(TRANSCEIVER,bindTypes.getTranceivers(), tpsByBind);
		createNewBind(RECEIVER,bindTypes.getReceivers(), tpsByBind);
		createNewBind(TRANSMITTER, bindTypes.getTransmitters(), tpsByBind);
	}
	private void createNewBind(SmppBindType type,int numbers,int tpsByBind) {
		for(int i=0;i<numbers;i++)
			bindManager.establishBind(connectorConfig, pduQueue,type,tpsByBind);
	}
	
	public void createNewBind(BindTypes bindTypes) {
		bindTypes.update(bindTypes);
		int newTpsByBind = maxReqPerSecond.calculateTpsByBind(bindTypes, connectorConfig.getThroughput());
		bindManager.updateBindTps(newTpsByBind);
		createNewBind(TRANSCEIVER,bindTypes.getTranceivers(), newTpsByBind);
		
		
		
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

	public void stopBind(int id) {
		bindManager.stopBind(id);
		
	}

	

}
