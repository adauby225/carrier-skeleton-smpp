package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVPASWD;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVSYSID;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.carrier.smpp.demo.EsmeSmppAccount;
import com.carrier.smpp.handler.pdu.request.DefaultEsmeBindRequestHandler;
import com.carrier.smpp.model.SmppAccount;
import com.carrier.smpp.model.SmppAccountRepository;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSessionConfiguration;


public class DefaultSmppSessionRequestHandlerTest {
	private static final String SYSTEM_ID = "carrierId";
	private static final String PASSWORD = "carrier-pass";
	private SmppAccountParamChecker systemIdParameter = new  SmppAccountParamChecker() {
		
		@Override
		public int check(SmppSessionConfiguration sessionConfiguration, SmppAccount configParams) {
			String systemId = sessionConfiguration.getSystemId();
			if(!systemId.equals(configParams.getSystemId()))
				return STATUS_INVSYSID;
			return STATUS_OK;
		}
	};
	private SmppAccountParamChecker passwordParameter = new SmppAccountParamChecker() {
		@Override
		public int check(SmppSessionConfiguration sessionConfiguration, SmppAccount configParams) {
			String password = sessionConfiguration.getPassword();
			if(!password.equals(configParams.getPassword()))
				return SmppConstants.STATUS_INVPASWD;
			return STATUS_OK;
		}
	};
	
	SmppAccountRepository configParamsRepository = (smppSessionConfig)-> new EsmeSmppAccount(SYSTEM_ID, PASSWORD);
	private List<SmppAccountParamChecker>parameters = Arrays.asList(systemIdParameter,passwordParameter);
	
	@Test
	public void testHandleRequestStatusOk() {
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(parameters
				, configParamsRepository);
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(TRANSCEIVER
				, SYSTEM_ID, PASSWORD);
		assertEquals(SmppConstants.STATUS_OK, (int)defaultEsmeBindRequestHandler.handleRequest(sessionConfiguration));
	}
	
	@Test
	public void testHandleRequestInvalidSystemId() {
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(parameters
				, configParamsRepository);
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(TRANSCEIVER
				, "inv_sys_id", PASSWORD);
		
		assertEquals(STATUS_INVSYSID, (int)defaultEsmeBindRequestHandler.handleRequest(sessionConfiguration));
	}
	
	@Test
	public void testHandleRequestInvalidPassword() {
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(parameters
				, configParamsRepository);
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(TRANSCEIVER
				, SYSTEM_ID, "inv_pass");
		assertEquals(STATUS_INVPASWD, (int)defaultEsmeBindRequestHandler.handleRequest(sessionConfiguration));
	}
	
	

}
