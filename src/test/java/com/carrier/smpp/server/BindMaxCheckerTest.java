package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class BindMaxCheckerTest {
	private static final String SYSTEM_ID = "sysId";
	private static final String PASSWORD = "p@sswd12";
	private static final int BIND_MAX = 3;
	private static final long SESSION_0 = 0;
	private static final long SESSION_1 = 1;
	private static final long SESSION_2 = 2;
	private EsmeSmppAccount smppAccount = new EsmeSmppAccount(SYSTEM_ID, PASSWORD,BIND_MAX);
	private EsmeSmppSession session0 = new EsmeSmppSession(SESSION_0, null, smppAccount);
	private EsmeSmppSession session1 = new EsmeSmppSession(SESSION_1, null, smppAccount);
	private EsmeSmppSession session2 = new EsmeSmppSession(SESSION_2, null, smppAccount);
	
	
	@Before
	public void resetSessionManager() throws NoSuchFieldException, SecurityException, IllegalArgumentException
	, IllegalAccessException {
		Field instance = SessionManager.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
	}
	
	
	@Test
	public void testBindMaxNotReached(){
		SessionManager sessionManager = SessionManager.getInstance();
		DefaultBindMaxChecker bindMaxChecker = new DefaultBindMaxChecker();
		sessionManager.addNewSession(SESSION_0, session0);
		sessionManager.addNewSession(SESSION_1, session1);
		sessionManager.addNewSession(SESSION_2, session2);
		
		int status = bindMaxChecker.check(createClientConfiguration(), smppAccount);
		assertEquals(SmppConstants.STATUS_BINDFAIL, status);
	}
	
	@Test
	public void testBindMaReached(){
		
	}
	
	private SmppSessionConfiguration createClientConfiguration() {
		SmppSessionConfiguration configuration = new SmppSessionConfiguration();
		configuration.setWindowSize(1);
		configuration.setName("Tester.Session.0");
		configuration.setType(TRANSCEIVER);
		configuration.setHost("localhost");
		configuration.setPort(2775);
		configuration.setConnectTimeout(1000);
		configuration.setBindTimeout(1000);
		configuration.setSystemId(SYSTEM_ID);
		configuration.setPassword(PASSWORD);
		configuration.getLoggingOptions().setLogBytes(true);
		return configuration;
	}

}
