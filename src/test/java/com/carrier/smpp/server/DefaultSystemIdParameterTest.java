package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVSYSID;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultSystemIdParameterTest {
	private static final String SYSTEM_ID = "carrierId";
	
	@Test
	public void testValidSystemId() {
		DefaultSystemIdParameter defaultSystemIdParameter =new DefaultSystemIdParameter();
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(
				TRANSCEIVER, SYSTEM_ID, "secret");
		EsmeSmppAccount account = new EsmeSmppAccount(SYSTEM_ID,"secret");
		assertEquals(STATUS_OK, defaultSystemIdParameter.check(sessionConfiguration, account)); 
	}
	
	@Test
	public void testInvalidSystemId() {
		DefaultSystemIdParameter defaultSystemIdParameter =new DefaultSystemIdParameter();
		SmppSessionConfiguration sessionConfiguration = new SmppSessionConfiguration(
				TRANSCEIVER, "invalid", "secret");
		EsmeSmppAccount account = new EsmeSmppAccount(SYSTEM_ID, "secret");
		assertEquals(STATUS_INVSYSID, defaultSystemIdParameter.check(sessionConfiguration, account));
	}
}
