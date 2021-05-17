package com.carrier.smpp.demo.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.executor.BindExecutor;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.handler.pdu.response.PduResponseHandler;
import com.carrier.smpp.outbound.client.BindTypes;
import com.carrier.smpp.outbound.client.CarrierSmppBind;
import com.carrier.smpp.outbound.client.SmppOutbound;
import com.carrier.smpp.outbound.client.OutBoundConfiguration;
import com.carrier.smpp.outbound.client.DefaultMaxTpsCalculator;
import com.carrier.smpp.outbound.client.SharedClientBootstrap;
import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;

public class Binds {
	private static final Logger logger = LogManager.getLogger(Binds.class);
	public static void main(String[] args) throws SmppInvalidArgumentException, IOException, InterruptedException, CloneNotSupportedException {
		OutBoundConfiguration settings = new OutBoundConfiguration("mason", "mason", "127.0.0.1", 34568);
		Map<Integer, ResponseHandler>submitsmRespStatusHandler = new HashMap<>();
		Map<Integer, ResponseHandler>respHandlers = new HashMap<>();
		
		submitsmRespStatusHandler.put(SmppConstants.STATUS_INVDSTADR,new SubmitSmRespInvalidDestHandler());
		submitsmRespStatusHandler.put(SmppConstants.STATUS_OK, new SubmitSmRespStatusOkHandler());
		respHandlers.put(SmppConstants.CMD_ID_SUBMIT_SM_RESP, new PduResponseHandler(submitsmRespStatusHandler));
		//map request form smsc
		Map<Integer, RequestHandler>reqHandlers = new HashMap<>();
		reqHandlers.put(SmppConstants.CMD_ID_DELIVER_SM, new deliverSmHandler());
		settings.setWindowSize(1);
        settings.setName("test.carrier.0");
        settings.setHost("127.0.0.1");
        
        
		BindTypes bindTypes = new BindTypes(1,0,0);
		settings.setBindTypes(bindTypes);
		settings.setThroughput(20);
		PduRequestSender pduRequestSender = new PduRequestSender();
		DefaultMaxTpsCalculator maxTps = new DefaultMaxTpsCalculator();
        SmppOutbound connector = new SmppOutbound(settings,BindExecutor::runBind
        		,pduRequestSender,maxTps,reqHandlers,respHandlers);
        connector.connect();
        String text160 = "\u20AC Lorem [ipsum] dolor sit amet, consectetur adipiscing elit. Proin feugiat, leo id commodo tincidunt, nibh diam ornare est, vitae accumsan risus lacus sed sem metus.";
        byte[] textBytes = CharsetUtil.encode(text160, CharsetUtil.CHARSET_GSM);
        SubmitSm sms = new SubmitSm();
        Address sourceAddress = new Address();
        Address destAddress = new Address();
        sourceAddress.setAddress("SENDER");
        destAddress.setAddress("225444040401");
        sms.setSourceAddress(sourceAddress);
        sms.setDestAddress(destAddress);
        sms.setShortMessage(textBytes);
        connector.addRequestFirst(sms);
        List<CarrierSmppBind>binds_1 = connector.getBinds();
    
        for(CarrierSmppBind bind: binds_1) {
        	logger.info("bind {} is bound: {} with tps {}",bind.getId(), bind.isUp(),bind.getTps());
        }
        //create new bind
        logger.info("Press any key to add new bind");
        System.in.read();
        connector.createNewBinds(new BindTypes(1, 0, 0));
        List<CarrierSmppBind>binds_2 = connector.getBinds();
        for(CarrierSmppBind bind: binds_2) {
        	logger.info("bind {} is bound: {} with tps {}",bind.getId(), bind.isUp(), bind.getTps());
        }
        logger.info("Press any key to unbind bind 1 ");
        System.in.read();
        connector.stopBind(1);
        logger.info("Press any key to unbind and close sessions");
        System.in.read();
        
        connector.disconnect();
        BindExecutor.stopAll();
        SharedClientBootstrap sharedClientBootStrap = SharedClientBootstrap.getInstance();
        sharedClientBootStrap.stopClientBootStrap();
        

	}

}
