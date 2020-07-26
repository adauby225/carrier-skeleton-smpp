package com.carrier.smpp.outbound.client;

import static com.carrier.smpp.util.Messages.UNBINDING;
import static com.carrier.smpp.util.Values.DEFAULT_ENQUIRE_LINK_INTERVAL;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.smsc.request.SmscPduRequestHandler;
import com.carrier.smpp.smsc.response.SmscPduResponseHandler;
import com.carrier.smpp.util.LoggingUtil;
import com.carrier.smpp.util.ThreadUtil;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.cloudhopper.smpp.util.SmppSessionUtil;

public class CarrierSmppBind implements Runnable{
	private Logger logger = LogManager.getLogger(CarrierSmppBind.class);
	private long timeToSleep = 100;
	private Long id;
	private PduQueue pduQueue;
	private SmppSessionConfiguration config;
	private AtomicBoolean unbound = new AtomicBoolean(false);
	private SmppSession session = null;
	private RequestSender requestSender;
	private Npi npi;
	private Ton ton;
	private int tps;
	private RequestSender enquireLinkSender;
	private int enquireLinkInterval = DEFAULT_ENQUIRE_LINK_INTERVAL;
	private final Map<Integer, SmscPduRequestHandler> smscReqHandlers;
	private final Map<Integer, SmscPduResponseHandler> smscResponseHandlers;
	private DefaultSmppSessionHandler sessionHandler=null;
	private CountDownLatch startSendingSignal;
	public CarrierSmppBind(PduQueue pduQueue, SmppSessionConfiguration config, RequestSender requestSender
			,RequestSender enquireLinkSender,Map<Integer, SmscPduRequestHandler> smscReqHandlers
			,Map<Integer, SmscPduResponseHandler> smscResponseHandlers,int tps) {

		this.pduQueue = pduQueue;
		this.config = config;
		this.requestSender =requestSender;
		this.enquireLinkSender = enquireLinkSender;
		this.tps = tps;
		this.smscReqHandlers = smscReqHandlers;
		this.smscResponseHandlers = smscResponseHandlers;
	}

	public Long getId() {
		return id;
	}


	@Override
	public void run() {

		while(!unbound.get()) {
			try {
				if(session != null && session.isBound()) {
					requestSender.send(session, pduQueue, tps);
					enquireLinkSender.send(session,enquireLinkInterval);
				}else reconnect();

				timeToSleep = 100;

			} catch (SmppTimeoutException | SmppChannelException |  UnrecoverablePduException  e) {

				logger.warn("Unable to connect: " + e.getMessage() + " " + LoggingUtil.toString(config, false));
				logger.debug("", e);
				destroySession();
				/*
				 * Wait 10 seconds before trying again...
				 */
				timeToSleep = 10000;

			}catch(InterruptedException e) {
				logger.error("[connection failure]" + e);
				Thread currentThread = Thread.currentThread();
				currentThread.interrupt();
				destroySession();

			}finally {
				if(!unbound.get()) 
					ThreadUtil.sleep(timeToSleep);
				
			}
		}
		logger.info(UNBINDING);
		unbind();
	}
	private void reconnect() throws SmppTimeoutException, SmppChannelException, UnrecoverablePduException, InterruptedException {
		destroySession();
		connect();
	}

	public void intialize() {
		sessionHandler= new ClientSmppSessionHandler(config.getName(),logger,pduQueue,smscReqHandlers,smscResponseHandlers);
	}
	private void connect() throws SmppTimeoutException,
	SmppChannelException, UnrecoverablePduException, InterruptedException {
		if (!unbound.get() &&(this.session == null || this.session.isClosed())) {
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
		return unbound.get();
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
		if(session!=null && session.isBound()) {
			session.unbind(1000);
			SmppSessionUtil.close(session);
			unbound.set(true);
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
				timeToSleep = 10000;
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

	public void setStartSendingSignal(CountDownLatch startSendingSignal) {
		this.startSendingSignal = startSendingSignal;

	}


}
