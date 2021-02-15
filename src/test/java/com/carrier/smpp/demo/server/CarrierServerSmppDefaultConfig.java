package com.carrier.smpp.demo.server;

import static com.carrier.smpp.util.SkeletonExecutors.getExecutor;
import static com.carrier.smpp.util.SkeletonExecutors.getMonitorExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.carrier.smpp.server.CarrierSmppServer;
import com.carrier.smpp.server.CarrierSmppServerHandler;
import com.carrier.smpp.server.DefaultCredentialChecker;
import com.carrier.smpp.server.SmppAccountParamCheckable;
import com.carrier.smpp.server.DefaultEsmeBindRequestHandler;
import com.cloudhopper.smpp.SmppConstants;

public class CarrierServerSmppDefaultConfig {

	public static void main(String[] args) throws HandlerException {
		Map<Integer, RequestHandler>requestHandlers = new HashMap<>();
		requestHandlers.put(SmppConstants.CMD_ID_SUBMIT_SM, new SubmitSmHandlerExple());
		requestHandlers.put(SmppConstants.CMD_ID_UNBIND, new UnbindHandlerExple());
		
		Map<Integer, ResponseHandler> responseHandlers = new HashMap<>();
		List<SmppAccountParamCheckable>checkers = Arrays.asList(new DefaultCredentialChecker());
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(
				checkers,new EsmeAccountRepositoryExple());
		
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(defaultEsmeBindRequestHandler
				,new EsmeAccountRepositoryExple(),new PduHandlersDemo());
		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, carrierSmppServerHandler);
		
		
		smppServer.start();
		smppServer.stop();

	}

}
