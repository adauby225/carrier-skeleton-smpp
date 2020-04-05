package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVSYSID;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultCredentialTest {
	private static final String SYSTEM_ID = "carrierId";
	
	@Test
	public void testValidSystemId() {
		DefaultCredentialChecker defaultCredentialChecker =new DefaultCredentialChecker();
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(
				TRANSCEIVER, SYSTEM_ID, "secret");
		EsmeSmppAccount account = new EsmeSmppAccount(SYSTEM_ID,"secret");
		assertEquals(STATUS_OK, defaultCredentialChecker.check(sessionConfiguration, account)); 
	}
	
	@Test
	public void testInvalidSystemId() {
		DefaultCredentialChecker defaultCredentialChecker =new DefaultCredentialChecker();
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(
				TRANSCEIVER, "invalid", "secret");
		EsmeSmppAccount account = new EsmeSmppAccount(SYSTEM_ID, "secret");
		assertEquals(STATUS_INVSYSID, defaultCredentialChecker.check(sessionConfiguration, account));
	}
}
