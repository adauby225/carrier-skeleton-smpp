package com.carrier.smpp.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.carrier.smpp.model.esme.EsmeAccount;

public class SessionManagerTest {

	@Test
	public void testAddNewSession() {
		long sessionId = 1203;
		EsmeSmppSession session = new EsmeSmppSession(null, new EsmeAccount());
		SessionManager sessionManager = SessionManager.getInstance();
		assertEquals(0, sessionManager.sessionsSize());
		sessionManager.addNewSession(sessionId, session);
		assertEquals(1, sessionManager.sessionsSize());
	}
}
