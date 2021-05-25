package com.carrier.smpp.outbound.client;

import static com.carrier.smpp.util.Messages.UNBINDING;
import static com.carrier.smpp.util.Values.DEFAULT_ENQUIRE_LINK_INTERVAL;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.pdu.request.dispatching.RequestManager;
import com.carrier.smpp.util.LoggingUtil;
import com.carrier.smpp.util.Messages;
import com.carrier.smpp.util.ThreadUtil;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;

public class CarrierSmppBind implements Runnable{
	private final Logger logger;
	private Long id;
	private RequestManager reqDispatcher;
	private SmppSessionConfiguration config;
	private boolean unbound = false;
	private SmppSession session = null;
	private RequestSender requestSender;
	private Npi npi;
	private Ton ton;
	private int tps;
	private RequestSender enquireLinkSender;
	private int enquireLinkInterval = DEFAULT_ENQUIRE_LINK_INTERVAL;
	private final Map<Integer, RequestHandler<PduRequest,PduResponse>> smscReqHandlers;
	private final ResponseHandler<PduAsyncResponse> asyncHandler;
	private DefaultSmppSessionHandler sessionHandler=null;
	public CarrierSmppBind(RequestManager reqDispatcher, SmppSessionConfiguration config, RequestSender requestSender
			,RequestSender enquireLinkSender,Map<Integer, RequestHandler<PduRequest,PduResponse>> smscReqHandlers
			,ResponseHandler<PduAsyncResponse> asyncHandler,int tps) {

		this.reqDispatcher = reqDispatcher;
		this.config = config;
		this.requestSender =requestSender;
		this.enquireLinkSender = enquireLinkSender;
		this.tps = tps;
		this.smscReqHandlers = smscReqHandlers;
		this.asyncHandler = asyncHandler;
		this.logger = LogManager.getLogger(config.getName());
	}

	public Long getId() {
		return id;
	}


	@Override
	public void run() {
		logger.info("inside bind {}", config.getName().concat("-"+config.getType().toString()));
		while(!unbound) {
			try {
				logger.info("{} trying to send request(s)", config.getName().concat("-"+config.getType().toString()));
				if(session != null && session.isBound()) {
					logger.info("{} trying to send submit_sm(s)", config.getName().concat("-"+config.getType().toString()));
					requestSender.send(session, reqDispatcher, tps);
					logger.info("{} sending enquire_link", config.getName().concat("-"+config.getType().toString()));
					enquireLinkSender.send(session,enquireLinkInterval);
				}else reconnect();

			} catch (SmppTimeoutException | SmppChannelException |  UnrecoverablePduException  e) {

				logger.warn("Unable to connect: " + e.getMessage() + " " + LoggingUtil.toString(config, false));
				logger.debug("", e);
				destroySession();
				
			}catch(InterruptedException e) {
				logger.error("[connection failure]" + e);
				Thread currentThread = Thread.currentThread();
				currentThread.interrupt();
				destroySession();

			/*}catch(Exception e) {
				logger.error(e);*/
			}finally {
				logger.info("{} trying pause", config.getName().concat("-"+config.getType().toString()));
				if(!unbound && reqDispatcher.sizeOfRequests()==0) 
					ThreadUtil.sleep(enquireLinkInterval);
			}
			if(unbound) 
				break;
			
		}
		logger.info("{} Stopping", config.getName());
		logger.info(UNBINDING);
		unbind();
	}
	private void reconnect() throws SmppTimeoutException, SmppChannelException, UnrecoverablePduException, InterruptedException {
		destroySession();
		connect();
	}

	public void intialize() {
		sessionHandler= new ClientSmppSessionHandler(config.getName(),logger,reqDispatcher,smscReqHandlers
				,asyncHandler,session);
	}
	private void connect() throws SmppTimeoutException,
	SmppChannelException, UnrecoverablePduException, InterruptedException {
		if (!unbound &&(this.session == null || this.session.isClosed())) {
			logger.info("connecting {}", this);
			SharedClientBootstrap sharedClientBootstrap = SharedClientBootstrap.getInstance();
			DefaultSmppClient clientBootstrap = sharedClientBootstrap.getClientBootstrap();
			this.session = clientBootstrap.bind(config, sessionHandler);
			logger.info("connected {}", this);
		}
	}

	public CarrierSmppBind self() {
		return this;
	}

	public boolean isUp() {
		if(session==null) return false;
		return session.isBound();
	}

	public boolean isUnbound() {
		return unbound;
	}


	public void setUnbound(boolean unbound) {
		this.unbound = unbound;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Npi getNpi() {
		return npi;
	}

	public void setNpi(Npi npi) {
		this.npi = npi;
	}

	public Ton getTon() {
		return ton;
	}

	public void setTon(Ton ton) {
		this.ton = ton;
	}

	public int getTps() {
		return tps;
	}

	public void setTps(int tps) {
		this.tps = tps;
	}

	public void unbind() {
		if(session!=null ) {
			if(session.isBound()) {
				logger.info(Messages.SENDING_UNBIND);
				session.unbind(1000);
			}
			logCounters();
			logger.info(Messages.SESSION_DESTRUCTION);
			destroySession();
			logger.info(Messages.DESTROYED_SESSION);

		}
	}

	public SmppBindType getType() {
		return config.getType();
	}
	private void destroySession() {
		try {
			if (session!=null ) {
				logger.debug("Cleaning up session... (final counters)");
				logCounters();
				session.destroy();
				session = null;
			}
		}catch(Exception e) {
			logger.warn("Destroy session error", e);
		}



	}

	private void logCounters() {
		if (session.hasCounters()) {
			logger.debug("tx-enquireLink: {}", session.getCounters().getTxEnquireLink());
			logger.debug("tx-submitSM: {}", session.getCounters().getTxSubmitSM());
			logger.debug("tx-deliverSM: {}", session.getCounters().getTxDeliverSM());
			logger.debug("tx-dataSM: {}", session.getCounters().getTxDataSM());
			logger.debug("rx-enquireLink: {}", session.getCounters().getRxEnquireLink());
			logger.debug("rx-submitSM: {}", session.getCounters().getRxSubmitSM());
			logger.debug("rx-deliverSM: {}", session.getCounters().getRxDeliverSM());
			logger.debug("rx-dataSM: {}", session.getCounters().getRxDataSM());
		}

	}
	@Override
	public String toString() {
		return LoggingUtil.toString(config, true);
	}

}
