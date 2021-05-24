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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.executor.BindExecutor;
import com.carrier.smpp.executor.ServiceExecutor;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.AsyncPduResponseHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.request.dispatching.RequestManager;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class SmppOutbound {
	private final Logger logger = LogManager.getLogger(SmppOutbound.class.getName());
	private OutBoundConfiguration connectorConfig;
	private OutboundSmppBindManager bindManager;
	private RequestManager reqDispatcher ;
	private Map<Long, CarrierSmppBind>binds=new HashMap<>();
	private final MaxRequestPerSecond maxReqPerSecond;
	private ThreadPoolExecutor respThreadPool = getNewCachedPool();
	public SmppOutbound(OutBoundConfiguration connectorConfig, ServiceExecutor serviceExecutor
			,RequestSender requestSender,MaxRequestPerSecond maxReqPerSecond
			,Map<Integer, RequestHandler<PduRequest, PduResponse>>smscReqHandlers
			,Map<Integer, ResponseHandler>smscresponseHandlers,RequestManager reqDispatcher) {
		this.connectorConfig = connectorConfig;
		this.bindManager = new OutboundSmppBindManager(binds,serviceExecutor,requestSender,smscReqHandlers
				,new AsyncPduResponseHandler(smscresponseHandlers,respThreadPool));
		this.maxReqPerSecond = maxReqPerSecond;
		this.respThreadPool.setCorePoolSize(500);
		this.respThreadPool.setMaximumPoolSize(1000);
		this.reqDispatcher = reqDispatcher;
		this.respThreadPool.setThreadFactory(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("PduResponseHandlerMonitorPool-"+ connectorConfig.getName());
				return t;
			}
		});
	}

	public SmppOutbound(OutBoundConfiguration connectorConfig, ServiceExecutor serviceExecutor
			,RequestSender requestSender,MaxRequestPerSecond maxReqPerSecond
			,Map<Integer, RequestHandler<PduRequest, PduResponse>>smscReqHandlers
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
			bindManager.establishBind(connectorConfig, reqDispatcher,type,tpsByBind);
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
		respThreadPool.awaitTermination(5, TimeUnit.SECONDS);
		logger.info("resThreadPool is terminated {} and shutdown {}",respThreadPool.isTerminated(), respThreadPool.isShutdown());
		respThreadPool.shutdownNow();
		logger.info("resThreadPool is shutdown now {}", respThreadPool.isShutdown());
		
	}

	public List<CarrierSmppBind> getBinds() {
		return bindManager.getListOfBinds();
	}

	public void addRequest(PduRequest pduRequest) {
		reqDispatcher.addRequest(pduRequest);
	}
	public long sizeOfRequest() {
		return reqDispatcher.sizeOfRequests();
	}

	public void stopBind(int id) {
		bindManager.stopBind(id);
	}

	



}
