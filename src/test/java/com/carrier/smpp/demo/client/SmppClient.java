package com.carrier.smpp.demo.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.outbound.client.BindTypes;
import com.carrier.smpp.outbound.client.CarrierSmppBind;
import com.carrier.smpp.outbound.client.CarrierSmppConnector;
import com.carrier.smpp.outbound.client.SharedClientBootstrap;
import com.carrier.smpp.outbound.client.ConnectorConfiguration;
import com.carrier.smpp.outbound.client.MaxRequestPerSecond;
import com.carrier.smpp.outbound.client.MaxTpsDefault;
import com.carrier.smpp.outbound.client.PduQueue;
import com.carrier.smpp.outbound.client.RequestSender;
import com.carrier.smpp.service.BindExecutor;
import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.cloudhopper.smpp.util.SmppSessionUtil;

public class SmppClient {
	private static final Logger logger = LogManager.getLogger(SmppClient.class);
	public static void main(String[] args) throws InterruptedException, SmppInvalidArgumentException {
		
		ConnectorConfiguration settings = new ConnectorConfiguration("sysId", "passwd", "127.0.0.1", 34567);
		
		settings.setWindowSize(1);
        settings.setName("test.carrier.0");
        settings.setHost("127.0.0.1");
        
        
		BindTypes bindTypes = new BindTypes(0,1,1);
		PduRequestSender pduRequestSender = new PduRequestSender();
		MaxTpsDefault maxTps = new MaxTpsDefault();
        CarrierSmppConnector connector = new CarrierSmppConnector(settings,bindTypes,BindExecutor::runBind
        		,pduRequestSender,maxTps);
        connector.connect();
        String text160 = "\u20AC Lorem [ipsum] dolor sit amet, consectetur adipiscing elit. Proin feugiat, leo id commodo tincidunt, nibh diam ornare est, vitae accumsan risus lacus sed sem metus.";
        byte[] textBytes = CharsetUtil.encode(text160, CharsetUtil.CHARSET_GSM);
        SubmitSm sms = new SubmitSm();
        Address sourceAddress = new Address();
        Address destAddress = new Address();
        sourceAddress.setAddress("SENDER");
        destAddress.setAddress("22544404040");
        sms.setSourceAddress(sourceAddress);
        sms.setDestAddress(destAddress);
        sms.setShortMessage(textBytes);
        connector.addRequestFirst(sms);
        List<CarrierSmppBind>binds = connector.getBinds();
        boolean isBound=false;
        for(CarrierSmppBind bind: binds) {
        	isBound =bind.isUp();
        	logger.info("bind is bound: " + true);
        }
        
        Thread.sleep(40000);
        
        connector.disconnect();
        BindExecutor.stopAll();
        SharedClientBootstrap sharedClientBootStrap = SharedClientBootstrap.getInstance();
        sharedClientBootStrap.stopClientBootStrap();
		
		
		
	}
	
}

class PduRequestSender implements RequestSender{
	private final Logger logger = LogManager.getLogger(PduRequestSender.class);
	@Override
	public void send(SmppSession session, PduQueue pduQueue, int tps) throws InterruptedException {
		if(session.isBound() && !pduQueue.isEmpty()) {
			try {
				
			SmppSessionConfiguration config=  session.getConfiguration();
			sendMessage(session,tps,canSubmit(config.getType()),pduQueue);
			}catch(UnrecoverablePduException  | SmppChannelException e) {
				SmppSessionUtil.close(session);
			}
		}
		
	}
	
	public boolean sendMessage(SmppSession session, int maxToSend
			,boolean canSend,PduQueue pduQueue) throws UnrecoverablePduException, SmppChannelException,InterruptedException {
		PduRequest asynchronSubmit=null;
		boolean submited=false;
		try {
			if(canSend) {
				for(int i=0 ;i<maxToSend;i++) {
					asynchronSubmit = pduQueue.takeFirstRequest();
					session.sendRequestPdu(asynchronSubmit,session.getConfiguration().getRequestExpiryTimeout(), false);				
					logger.info(asynchronSubmit);	
				}
				submited=true;
			}
		}catch( RecoverablePduException | SmppTimeoutException e) {
			logger.error(e);
		}finally {
			if(!submited && asynchronSubmit!=null)
				pduQueue.addRequestFirst(asynchronSubmit);
		}

		return submited;
	}
	
	public boolean canSubmit(SmppBindType type) {
		return type.equals(SmppBindType.TRANSCEIVER)||type.equals(SmppBindType.TRANSMITTER);
	}

}
