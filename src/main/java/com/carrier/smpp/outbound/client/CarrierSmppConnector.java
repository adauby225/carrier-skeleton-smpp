package com.carrier.smpp.outbound.client;

import static com.cloudhopper.smpp.SmppBindType.RECEIVER;
import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppBindType.TRANSMITTER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.carrier.smpp.executor.ServiceExecutor;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.AsyncPduResponseHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.pdu.PduRequest;

public class CarrierSmppConnector {
	
	private ConnectorConfiguration connectorConfig;
	private OutboundSmppBindManager bindManager;
	private PduQueue pduQueue = new PduQueue();
	private Map<Long, CarrierSmppBind>binds=new HashMap<>();
	private final MaxRequestPerSecond maxReqPerSecond;
	
	public CarrierSmppConnector(ConnectorConfiguration connectorConfig, ServiceExecutor serviceExecutor
			,RequestSender requestSender,MaxRequestPerSecond maxReqPerSecond
			,Map<Integer, RequestHandler>smscReqHandlers,Map<Integer, ResponseHandler>smscresponseHandlers) {
		this.connectorConfig = connectorConfig;
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor,requestSender,smscReqHandlers
				,new AsyncPduResponseHandler(smscresponseHandlers));
		this.maxReqPerSecond = maxReqPerSecond;
	}

	public void connect() {
		BindTypes bindTypes = connectorConfig.getBindTypes();
		int tpsByBind =maxReqPerSecond.calculateTpsByBind(bindTypes, connectorConfig.getThroughput());
		createNewBind(TRANSCEIVER,bindTypes.getTransceivers(), tpsByBind);
		createNewBind(RECEIVER,bindTypes.getReceivers(), tpsByBind);
		createNewBind(TRANSMITTER, bindTypes.getTransmitters(), tpsByBind);
	}
	private void createNewBind(SmppBindType type,int numbers,int tpsByBind) {
		for(int i=0;i<numbers;i++)
			bindManager.establishBind(connectorConfig, pduQueue,type,tpsByBind);
	}
	
	public void createNewBinds(BindTypes bindTypes) {
		connectorConfig.updateBindTypes(bindTypes);
		int newTpsByBind = maxReqPerSecond.calculateTpsByBind(connectorConfig.getBindTypes(), connectorConfig.getThroughput());
		bindManager.updateBindTps(newTpsByBind);
		createNewBind(TRANSCEIVER,bindTypes.getTransceivers(), newTpsByBind);
		createNewBind(RECEIVER,bindTypes.getReceivers(), newTpsByBind);
		createNewBind(TRANSMITTER, bindTypes.getTransmitters(), newTpsByBind);
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
	
	public int sizeOfRequest() {
		return pduQueue.size();
	}

	

}
