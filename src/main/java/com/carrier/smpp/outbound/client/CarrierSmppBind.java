package com.carrier.smpp.outbound.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;

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
		session.close();

	}

	private void connect() throws SmppTimeoutException,
	SmppChannelException, UnrecoverablePduException, InterruptedException {
		DefaultSmppSessionHandler sessionHandler=null;
		if (!unbound &&(session == null || session.isClosed())) {
			sessionHandler= new ClientSmppSessionHandler(settings.getName(),logger,pduQueue);
			session = clientBootstrap.bind(settings, sessionHandler);
		}
	}

}
