package com.carrier.smpp.demo.server;

import static com.carrier.util.SkeletonExecutors.getExecutor;
import static com.carrier.util.SkeletonExecutors.getMonitorExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.esme.response.EsmeResponseHandler;
import com.carrier.smpp.server.CarrierSmppServer;
import com.carrier.smpp.server.CarrierSmppServerHandler;
import com.carrier.smpp.server.ConfigParameter;
import com.carrier.smpp.server.DefaultEsmeBindRequestHandler;
import com.carrier.smpp.server.DefaultPasswordParameter;
import com.carrier.smpp.server.DefaultSystemIdParameter;
import com.carrier.smpp.server.HandlerException;
import com.cloudhopper.smpp.SmppConstants;

public class CarrierServerSmppDefaultConfig {

	public static void main(String[] args) throws HandlerException {
		Map<Integer, EsmeRequestHandler>requestHandlers = new HashMap<>();
		requestHandlers.put(SmppConstants.CMD_ID_SUBMIT_SM, new SubmitSmHandlerExple());
		requestHandlers.put(SmppConstants.CMD_ID_UNBIND, new UnbindHandlerExple());
		
		Map<Integer, EsmeResponseHandler> responseHandlers = new HashMap<>();
		List<ConfigParameter>parameters = Arrays.asList(new DefaultSystemIdParameter(),new DefaultPasswordParameter());
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(
				parameters,new EsmeAccountRepositoryExple());
		
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(defaultEsmeBindRequestHandler
				,new EsmeAccountRepositoryExple(),new PduHandlersDemo());
		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, carrierSmppServerHandler);
		
		
		smppServer.start();
		smppServer.stop();

	}

}
