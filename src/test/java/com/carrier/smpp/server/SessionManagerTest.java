package com.carrier.smpp.server;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import com.carrier.smpp.model.esme.EsmeSmppAccount;

public class SessionManagerTest {

	@Before
	public void resetSessionManager() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instance = SessionManager.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
	}
	
	@Test
	public void testAddNewSession() {
		long sessionId0 = 1203;
		long sessionId1 = 1204;

		EsmeSmppSession session0 = new EsmeSmppSession(null, new EsmeSmppAccount());
		EsmeSmppSession session1 = new EsmeSmppSession(null, new EsmeSmppAccount());
		SessionManager sessionManager = SessionManager.getInstance();
		assertEquals(0, sessionManager.sessionsSize());
		sessionManager.addNewSession(sessionId0, session0);
		sessionManager.addNewSession(sessionId1, session1);
		assertEquals(2, sessionManager.sessionsSize());
	}

	@Test
	public void testRemoveSession() {
		long sessionId0 = 1203;
		long sessionId1 = 1204;
		EsmeSmppSession session0 = new EsmeSmppSession(null, new EsmeSmppAccount());
		EsmeSmppSession session1 = new EsmeSmppSession(null, new EsmeSmppAccount());
		SessionManager sessionManager = SessionManager.getInstance();
		assertEquals(0, sessionManager.sessionsSize());
		sessionManager.addNewSession(sessionId0, session0);
		sessionManager.addNewSession(sessionId1, session1);
		sessionManager.removeSession(sessionId0);

		assertEquals(1, sessionManager.sessionsSize());
	}
}
