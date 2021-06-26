package com.carrier.smpp.demo.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.executor.BindExecutor;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.PduResponseHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.outbound.client.BindTypes;
import com.carrier.smpp.outbound.client.CarrierSmppBind;
import com.carrier.smpp.outbound.client.DefaultMaxTpsCalculator;
import com.carrier.smpp.outbound.client.OutBoundConfiguration;
import com.carrier.smpp.outbound.client.RequestSender;
import com.carrier.smpp.outbound.client.SharedClientBootstrap;
import com.carrier.smpp.outbound.client.SmppOutbound;
import com.carrier.smpp.pdu.request.dispatching.RequestCollections;
import com.carrier.smpp.pdu.request.dispatching.RequestManager;
import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.DeliverSm;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.cloudhopper.smpp.util.SmppSessionUtil;

public class SmppClient {
	private static final Logger logger = LogManager.getLogger(SmppClient.class);
	public static void main(String[] args) throws InterruptedException, SmppInvalidArgumentException, IOException, CloneNotSupportedException {

		OutBoundConfiguration settings = new OutBoundConfiguration("mason", "mason", "localhost", 34568);
		//map responseHandlers
		Map<Integer, ResponseHandler>submitsmRespStatusHandler = new HashMap<>();
		Map<Integer, ResponseHandler>respHandlers = new HashMap<>();

		submitsmRespStatusHandler.put(SmppConstants.STATUS_INVDSTADR,new SubmitSmRespInvalidDestHandler());
		submitsmRespStatusHandler.put(SmppConstants.STATUS_OK, new SubmitSmRespStatusOkHandler());
		respHandlers.put(SmppConstants.CMD_ID_SUBMIT_SM_RESP, new PduResponseHandler(submitsmRespStatusHandler));
		//map request form smsc
		Map<Integer, RequestHandler<PduRequest, PduResponse>>reqHandlers = new HashMap<>();
		reqHandlers.put(SmppConstants.CMD_ID_DELIVER_SM, new deliverSmHandler());
		settings.setWindowSize(100);
		/*settings.setName("test.carrier.0");
		settings.setHost("localhost");
		settings.setRemotePort(34568);*/
		settings.setName("test.carrier.0");
		settings.setHost("154.68.5.50");
		settings.setRemoteHost("test.arolitec.com");
		settings.setRemotePort(2775);
		settings.setLogin("9BB3F263");
		settings.setPassword("8FA1009B");
		settings.setThroughput(100000);


		BindTypes bindTypes = new BindTypes(0,1,1);
		settings.setBindTypes(bindTypes);
		PduRequestSender pduRequestSender = new PduRequestSender();
		DefaultMaxTpsCalculator maxTps = new DefaultMaxTpsCalculator();
		SmppOutbound connector = new SmppOutbound(settings,BindExecutor::runBind
				,pduRequestSender, maxTps,reqHandlers,respHandlers, new RequestCollections());
		String text160 = "\u20AC Lorem [ipsum] dolor sit amet, consectetur adipiscing elit. Proin feugiat, leo id commodo tincidunt, nibh diam ornare est, vitae accumsan risus lacus sed sem metus.";
		byte[] textBytes = CharsetUtil.encode(text160, CharsetUtil.CHARSET_GSM);
		for(int i=0;i<1000000;i++) {
			SubmitSm sms = new SubmitSm();
			Address sourceAddress = new Address();
			Address destAddress = new Address();
			/*sourceAddress.setAddress("SENDER");
		destAddress.setAddress("22544404040");*/
			sourceAddress.setAddress("AROLIGROUP");
			destAddress.setAddress("2250144419304");
			sms.setSourceAddress(sourceAddress);
			sms.setDestAddress(destAddress);
			sms.setShortMessage(textBytes);
			sms.setRegisteredDelivery((byte)0);
			connector.addRequest(sms);
		}
		connector.connect();
		List<CarrierSmppBind>binds = connector.getBinds();
		for(CarrierSmppBind bind: binds)
			logger.info("bind is bound: " + bind.isUp());


		logger.info("Press any key to unbind and close sessions");
		System.in.read();

		connector.disconnect();
		BindExecutor.stopAll();
		SharedClientBootstrap sharedClientBootStrap = SharedClientBootstrap.getInstance();
		sharedClientBootStrap.stopClientBootStrap();

	}

}

class PduRequestSender implements RequestSender{
	private final Logger logger = LogManager.getLogger(PduRequestSender.class);
	@Override
	public void send(SmppSession session, RequestManager reqDispatcher, int tps) throws InterruptedException {
		if(session.isBound() && reqDispatcher.sizeOfRequests()>0) {
			try {
				SmppSessionConfiguration config=  session.getConfiguration();
				sendMessage(session,tps,canSubmit(config.getType()),reqDispatcher);
			}catch(UnrecoverablePduException  | SmppChannelException e) {
				SmppSessionUtil.close(session);
			}
		}

	}

	public boolean sendMessage(SmppSession session, long maxToSend
			,boolean canSend,RequestManager reqDispatcher) throws UnrecoverablePduException, SmppChannelException,InterruptedException {
		Optional<PduRequest> asynchronSubmit=Optional.empty();
		PduRequest request=null;
		boolean submited=false;
		try {
			if(canSend && reqDispatcher.sizeOfRequests()>0) {
				if(maxToSend > reqDispatcher.sizeOfRequests())
					maxToSend = reqDispatcher.sizeOfRequests();

				for(int i=0 ;i<maxToSend;i++) {
					asynchronSubmit = reqDispatcher.nextRequest();
					if(asynchronSubmit.isPresent()) {
						request = asynchronSubmit.get();
						session.sendRequestPdu(request,session.getConfiguration().getRequestExpiryTimeout(), false);				
						logger.info(request);	
					}
				}
				submited=true;
			}
		}catch( RecoverablePduException | SmppTimeoutException e) {
			logger.error(e);
		}finally {
			if(!submited && request!=null)
				reqDispatcher.addRequest(asynchronSubmit.get());
		}

		return submited;
	}

	public boolean canSubmit(SmppBindType type) {
		return type.equals(SmppBindType.TRANSCEIVER)||type.equals(SmppBindType.TRANSMITTER);
	}

	@Override
	public void send(SmppSession session, int requestInterval) throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}



}
class SubmitSmRespStatusOkHandler implements ResponseHandler<PduAsyncResponse>{
	private final Logger logger = LogManager.getLogger(SubmitSmRespStatusOkHandler.class);
	@Override
	public void handleResponse(PduAsyncResponse pduAsyncResponse) {
		SubmitSmResp resp = (SubmitSmResp)pduAsyncResponse.getResponse();
		logger.info("handling submitSm resp: "+resp);
	}

}

class SubmitSmRespInvalidDestHandler implements ResponseHandler<PduAsyncResponse>{
	private final Logger logger = LogManager.getLogger(SubmitSmRespStatusOkHandler.class);
	@Override
	public void handleResponse(PduAsyncResponse pduAsyncResponse) {
		SubmitSmResp resp = (SubmitSmResp)pduAsyncResponse.getResponse();
		logger.info("handling submitSm resp: "+resp);
	}

}



class deliverSmHandler implements RequestHandler<PduRequest,PduResponse> {
	private final Logger logger = LogManager.getLogger(deliverSmHandler.class);
	@Override
	public PduResponse handleRequest(PduRequest pduRequest) {
		DeliverSm deliver = (DeliverSm)pduRequest;
		logger.info("handling deliverSm: " + deliver);
		return deliver.createResponse();
	}

}

