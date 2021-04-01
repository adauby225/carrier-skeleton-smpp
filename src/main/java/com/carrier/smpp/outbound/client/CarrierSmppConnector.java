package com.carrier.smpp.outbound.client;

import static com.carrier.smpp.util.SkeletonThreadPools.getNewCachedPool;
import static com.cloudhopper.smpp.SmppBindType.RECEIVER;
import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppBindType.TRANSMITTER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
	private ThreadPoolExecutor respThreadPool = getNewCachedPool();
	public CarrierSmppConnector(ConnectorConfiguration connectorConfig, ServiceExecutor serviceExecutor
			,RequestSender requestSender,MaxRequestPerSecond maxReqPerSecond
			,Map<Integer, RequestHandler>smscReqHandlers
			,Map<Integer, ResponseHandler>smscresponseHandlers) {
		this.connectorConfig = connectorConfig;
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor,requestSender,smscReqHandlers
				,new AsyncPduResponseHandler(smscresponseHandlers,respThreadPool));
		this.maxReqPerSecond = maxReqPerSecond;
		this.respThreadPool.setCorePoolSize(500);
		this.respThreadPool.setMaximumPoolSize(1000);
		this.respThreadPool.setThreadFactory(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("PduResponseHandlerMonitorPool-"+ connectorConfig.getName());
				return t;
			}
		});
	}

	public CarrierSmppConnector(ConnectorConfiguration connectorConfig, ServiceExecutor serviceExecutor
			,RequestSender requestSender,MaxRequestPerSecond maxReqPerSecond
			,Map<Integer, RequestHandler>smscReqHandlers
			,Map<Integer, ResponseHandler>smscresponseHandlers, int maxPoolSize) {
		this.connectorConfig = connectorConfig;
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor,requestSender,smscReqHandlers
				,new AsyncPduResponseHandler(smscresponseHandlers,respThreadPool));
		this.maxReqPerSecond = maxReqPerSecond;
		this.respThreadPool.setCorePoolSize(maxPoolSize);
		this.respThreadPool.setMaximumPoolSize(maxPoolSize);
		this.respThreadPool.setThreadFactory(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("PduResponseHandlerMonitorPool-"+ connectorConfig.getName());
				return t;
			}
		});
	}

	public void connect() throws CloneNotSupportedException {
		BindTypes bindTypes = connectorConfig.getBindTypes();
		int tpsByBind =maxReqPerSecond.calculateTpsByBind(bindTypes, connectorConfig.getThroughput());
		createNewBind(TRANSCEIVER,bindTypes.getTransceivers(), tpsByBind);
		createNewBind(RECEIVER,bindTypes.getReceivers(), tpsByBind);
		createNewBind(TRANSMITTER, bindTypes.getTransmitters(), tpsByBind);
	}
	private void createNewBind(SmppBindType type,int numbers,int tpsByBind) throws CloneNotSupportedException {
		for(int i=0;i<numbers;i++)
			bindManager.establishBind(connectorConfig, pduQueue,type,tpsByBind);
	}

	public void createNewBinds(BindTypes bindTypes) throws CloneNotSupportedException {
		connectorConfig.updateBindTypes(bindTypes);
		int newTpsByBind = maxReqPerSecond.calculateTpsByBind(connectorConfig.getBindTypes(), connectorConfig.getThroughput());
		bindManager.updateBindTps(newTpsByBind);
		createNewBind(TRANSCEIVER,bindTypes.getTransceivers(), newTpsByBind);
		createNewBind(RECEIVER,bindTypes.getReceivers(), newTpsByBind);
		createNewBind(TRANSMITTER, bindTypes.getTransmitters(), newTpsByBind);
	}

	public void disconnect() throws InterruptedException {
		bindManager.unbind();
		respThreadPool.shutdown();
		respThreadPool.awaitTermination(10, TimeUnit.SECONDS);
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
