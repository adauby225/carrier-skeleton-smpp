package com.carrier.smpp.outbound.client;

import static com.carrier.smpp.util.Values.DEFAULT_CARRIER_REQUEST_EXPIRY_TIMEOUT;
import static com.carrier.smpp.util.Values.DEFAULT_CARRIER_WINDOW_MONITOR_TIMEOUT;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.carrier.smpp.executor.ServiceExecutor;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.request.dispatching.RequestManager;
import com.carrier.smpp.util.Messages;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class OutboundSmppBindManager implements Connection<OutBoundConfiguration> {
	private static AtomicLong bindIds = new  AtomicLong(1);
	private ServiceExecutor serviceExecutor;
	private Map<Long, CarrierSmppBind> binds;
	private final RequestSender requestSender;
	private final Map<Integer, RequestHandler<PduRequest, PduResponse>> smscReqHandlers;
	private final ResponseHandler<PduAsyncResponse>asyncRespHandler;
	public OutboundSmppBindManager(Map<Long, CarrierSmppBind> binds, ServiceExecutor serviceExecutor
			, RequestSender requestSender, Map<Integer, RequestHandler<PduRequest, PduResponse>> smscReqHandlers
			, ResponseHandler<PduAsyncResponse>asyncRespHandler) {
		this.serviceExecutor = serviceExecutor;
		this.binds = binds;
		this.requestSender = requestSender;
		this.smscReqHandlers = smscReqHandlers;
		this.asyncRespHandler = asyncRespHandler;
	}

	@Override
	public void establishBind(OutBoundConfiguration settings,RequestManager reqDispatcher, SmppBindType type,int tps) throws CloneNotSupportedException {
		SmppSessionConfiguration config = getSessionConfig(settings, type);
		CarrierSmppBind bind =new CarrierSmppBind(reqDispatcher, config, (RequestSender)requestSender.clone()
				,(DefaultEnquireLinkSender) new DefaultEnquireLinkSender(config.getName()).clone(),smscReqHandlers,asyncRespHandler, tps);
		bind.setId(bindIds.getAndIncrement());
		bind.intialize();
		serviceExecutor.execute(bind);
		binds.put(bind.getId(), bind);
	}

	public SmppSessionConfiguration getSessionConfig(OutBoundConfiguration connectorConfig,SmppBindType type) {
		SmppSessionConfiguration config = new SmppSessionConfiguration(type, connectorConfig.getLogin(), connectorConfig.getPassword());
		config.setName(connectorConfig.getName());
		config.setHost(connectorConfig.getRemoteHost());
		config.setPort(connectorConfig.getRemotePort());
		config.setWindowSize(connectorConfig.getWindowSize());
		config.setRequestExpiryTimeout(connectorConfig.getRequestExpiryTimeout());
		config.setWindowMonitorInterval(connectorConfig.getWindowMonitorInterval());
		config.setCountersEnabled(true);
		config.getLoggingOptions().setLogBytes(false);
		config.getLoggingOptions().setLogPdu(false);
		if(!connectorConfig.isHostEmpty())
			config.setLocalAddress(connectorConfig.getHost());
		return config;
	}

	public synchronized void unbind() {
		for(CarrierSmppBind bind: binds.values()) 
			bind.setUnbound(true);

	}

	public List<CarrierSmppBind> getListOfBinds() {
		return binds.values().stream().map(CarrierSmppBind::self).collect(toList());
	}

	public void updateBindTps(int newTps) {
		for(CarrierSmppBind bind : binds.values()) {
			bind.setTps(newTps);
		}
	}

	@Override
	public void establishBind(OutBoundConfiguration t, RequestManager reqDispatcher, int tps) {
		throw new UnsupportedOperationException(Messages.UNAUTHORIZED_OPERATION);
	}

	public void stopBind(long id) {
		CarrierSmppBind bind=null;
		if(binds!=null && !binds.isEmpty()) {
			bind = binds.remove(id);
			bind.setUnbound(true);
		}

	}



}
