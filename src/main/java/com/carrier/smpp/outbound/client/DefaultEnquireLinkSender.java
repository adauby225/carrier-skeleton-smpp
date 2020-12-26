package com.carrier.smpp.outbound.client;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.util.Messages;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.cloudhopper.smpp.util.SmppSessionUtil;

public class DefaultEnquireLinkSender implements RequestSender {
	private LocalDateTime lastSent;
	private boolean firstSent=true;
	private final Logger logger;
	
	public DefaultEnquireLinkSender(String loggerName) {
		super();
		if(loggerName== null)
			logger = LogManager.getLogger(DefaultEnquireLinkSender.class.getName());
		else logger = LogManager.getLogger(loggerName);
		
	}
	
	@Override
	public void send(SmppSession session, PduQueue pduQueue, int tps) throws InterruptedException {
		throw new UnsupportedOperationException(Messages.UNAUTHORIZED_OPERATION);
	}

	public static long calculateDurationInMilliSecond(LocalDateTime from, LocalDateTime to) {
		return ChronoUnit.MILLIS.between(from, to);
	}
	@Override
	public void send(SmppSession session,int requestInterval) throws InterruptedException {
		if(session!=null && session.isBound()) {
			try {
				if(firstSent) {
					sendEnquireLink(session);
					firstSent = false;
				}else if(calculateDurationInMilliSecond(lastSent, LocalDateTime.now())>=requestInterval){
					sendEnquireLink(session);
				}
			}catch(UnrecoverablePduException  | SmppChannelException e) {
				logger.error(e);
				SmppSessionUtil.close(session);
			}
		}
	}
	
	private void sendEnquireLink(SmppSession session) throws UnrecoverablePduException, SmppChannelException, InterruptedException{
		EnquireLink enquireLink = new EnquireLink();
		lastSent = LocalDateTime.now();
		try {
			SmppSessionConfiguration config = session.getConfiguration();
			logger.info("[request sent] : {}",enquireLink);
			session.sendRequestPdu(enquireLink, config.getRequestExpiryTimeout(), false);
		}catch(SmppTimeoutException | RecoverablePduException e) {
			logger.warn(e.getMessage());
		}
	}

}
