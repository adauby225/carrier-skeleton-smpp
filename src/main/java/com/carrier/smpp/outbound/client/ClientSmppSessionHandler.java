package com.carrier.smpp.outbound.client;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public class ClientSmppSessionHandler extends DefaultSmppSessionHandler {
	private final String bindType; 
	private final Logger logger ;
	private PduQueue pduQueue;
	private CountDownLatch allRequestResponseReceivedSignal = new CountDownLatch(1);

	public ClientSmppSessionHandler(String bindType,Logger logger, PduQueue pduQueue) {
		this.bindType = bindType;
		this.logger = logger;
		this.pduQueue = pduQueue;
	}

	@Override
	public void firePduRequestExpired(PduRequest pduRequest) {
		

	}

	@Override
	public PduResponse firePduRequestReceived(PduRequest pduRequest) {
		
		
		return pduRequest.createResponse();
	
	}

	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) { 
		
	}

	@Override
	public void fireChannelUnexpectedlyClosed() {
		logger.error(bindType +": unexpected close occurred...");
		allRequestResponseReceivedSignal.countDown();
	}


}
