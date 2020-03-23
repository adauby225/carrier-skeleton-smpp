package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVPASWD;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class PassworSystemIdParameterTest {
	private static final String SYSTEM_ID = "sysId";
	private static final String PASSWORD = "secret";
	
	@Test
	public void testValidPassword() {
		DefaultPasswordParameter passwordParam = new DefaultPasswordParameter();
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(
				TRANSCEIVER, SYSTEM_ID, PASSWORD);
		EsmeSmppAccount configParams = new EsmeSmppAccount(SYSTEM_ID, PASSWORD);
		assertEquals(STATUS_OK, passwordParam.check(sessionConfiguration, configParams)); 
	}
	
	public void testInvalidPassword() {
		DefaultPasswordParameter passwordParam = new DefaultPasswordParameter();
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(
				TRANSCEIVER, SYSTEM_ID, "invalid");
		EsmeSmppAccount configParams = new EsmeSmppAccount(SYSTEM_ID, PASSWORD);
		assertEquals(STATUS_INVPASWD, passwordParam.check(sessionConfiguration, configParams)); 
	}
}
