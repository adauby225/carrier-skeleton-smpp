package com.carrier.smpp.outbound.client;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private Long id;
	private DefaultSmppClient clientBootstrap;
	private PduQueue pduQueue;
	private SmppOutboundSettings settings;
	private boolean unbound = false;
	private SmppSession session = null;
	

	public CarrierSmppBind(Long id,DefaultSmppClient clientBootstrap, PduQueue pduQueue
			, SmppOutboundSettings settings) {
		this.id = id;
		this.clientBootstrap = clientBootstrap;
		this.pduQueue = pduQueue;
		this.settings = settings;
	}

	public Long getId() {
		return id;
	}

	@Override
	public void run() {
		
		while(!unbound) {
			try {
				connect();
			} catch (SmppTimeoutException |  UnrecoverablePduException  e) {
				logger.info(e);

			}catch(InterruptedException e) {
				logger.warn(e);
				session.close();
				Thread currentThread = Thread.currentThread();
				currentThread.interrupt();

			} catch (SmppChannelException e) {
				logger.warn(e);
			}
			if(unbound)
				break;
		}
		logger.info("Stopping. . .");
		SmppSessionUtil.close(session);

	}

	private void connect() throws SmppTimeoutException,
	SmppChannelException, UnrecoverablePduException, InterruptedException {
		DefaultSmppSessionHandler sessionHandler=null;
		if (!unbound &&(this.session == null || this.session.isClosed())) {
			sessionHandler= new ClientSmppSessionHandler(settings.getName(),logger,pduQueue);
			this.session = clientBootstrap.bind((SmppSessionConfiguration)settings, sessionHandler);
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

	public void unbind() {
		if(session!=null && session.isBound())
			session.unbind(5000);
	}
	

}
