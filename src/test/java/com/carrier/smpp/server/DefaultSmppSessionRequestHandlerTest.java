package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVPASWD;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVSYSID;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.carrier.smpp.model.esme.ConfigParamsRepository;
import com.carrier.smpp.model.esme.EsmeSessionConfigParams;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSessionConfiguration;


public class DefaultSmppSessionRequestHandlerTest {
	private static final String SYSTEM_ID = "carrierId";
	private static final String PASSWORD = "carrier-pass";
	private ConfigParameter systemIdParameter = new  ConfigParameter() {
		
		@Override
		public int check(SmppSessionConfiguration sessionConfiguration, EsmeSessionConfigParams configParams) {
			String systemId = sessionConfiguration.getSystemId();
			if(!systemId.equals(configParams.getSystemId()))
				return STATUS_INVSYSID;
			return STATUS_OK;
		}
	};
	private ConfigParameter passwordParameter = new ConfigParameter() {
		@Override
		public int check(SmppSessionConfiguration sessionConfiguration, EsmeSessionConfigParams configParams) {
			String password = sessionConfiguration.getPassword();
			if(!password.equals(configParams.getPassword()))
				return SmppConstants.STATUS_INVPASWD;
			return STATUS_OK;
		}
	};
	
	ConfigParamsRepository configParamsRepository = ()-> new EsmeSessionConfigParams(SYSTEM_ID, PASSWORD);
	private List<ConfigParameter>parameters = Arrays.asList(systemIdParameter,passwordParameter);
	
	@Test
	public void testHandleRequestStatusOk() {
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(parameters
				, configParamsRepository);
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(TRANSCEIVER
				, SYSTEM_ID, PASSWORD);
		assertEquals(SmppConstants.STATUS_OK, defaultEsmeBindRequestHandler.handleRequest(sessionConfiguration));
	}
	
	@Test
	public void testHandleRequestInvalidSystemId() {
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(parameters
				, configParamsRepository);
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(TRANSCEIVER
				, "inv_sys_id", PASSWORD);
		
		assertEquals(STATUS_INVSYSID, defaultEsmeBindRequestHandler.handleRequest(sessionConfiguration));
	}
	
	@Test
	public void testHandleRequestInvalidPassword() {
		DefaultEsmeBindRequestHandler defaultEsmeBindRequestHandler = new DefaultEsmeBindRequestHandler(parameters
				, configParamsRepository);
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(TRANSCEIVER
				, SYSTEM_ID, "inv_pass");
		assertEquals(STATUS_INVPASWD, defaultEsmeBindRequestHandler.handleRequest(sessionConfiguration));
	}
	
	

}
