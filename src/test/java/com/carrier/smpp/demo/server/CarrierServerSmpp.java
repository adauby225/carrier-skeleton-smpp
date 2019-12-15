package com.carrier.smpp.demo.server;

import static com.carrier.util.SkeletonExecutors.getExecutor;
import static com.carrier.util.SkeletonExecutors.getMonitorExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.carrier.smpp.Carrier;
import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.esme.response.EsmeResponseHandler;
import com.carrier.smpp.server.CarrierSmppEntity;
import com.carrier.smpp.server.CarrierSmppServer;
import com.carrier.smpp.server.CarrierSmppServerHandler;
import com.carrier.smpp.server.HandlerException;
import com.cloudhopper.smpp.SmppConstants;

public class CarrierServerSmpp {
	
	public static void main(String[] args) throws HandlerException {
		Map<Integer, EsmeRequestHandler>requestHandlers = new HashMap<>();
		Map<Integer, EsmeResponseHandler>responseHandlers = new HashMap<>();
		requestHandlers.put(SmppConstants.CMD_ID_SUBMIT_SM, new SubmitSmHandlerExple());
		requestHandlers.put(SmppConstants.CMD_ID_UNBIND, new UnbindHandlerExple());
		
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new EsmeBindRequestHandlerExple(), new EsmeAccountRepositoryExple(),requestHandlers
				,responseHandlers);
		
		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, new SmppSrvConfigLoaderExple(), carrierSmppServerHandler);
		smppServer.start();
		smppServer.stop();
	}

}
