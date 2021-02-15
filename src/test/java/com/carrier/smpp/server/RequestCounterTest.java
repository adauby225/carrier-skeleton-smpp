package com.carrier.smpp.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.carrier.smpp.util.RequestCounter;

public class RequestCounterTest {
	private int authorizedTps = 100;
	private int tpsOverflow = 101;
	@Test
	public void testAuthorizedTps() {
		RequestCounter requestCounter = new RequestCounter();
		boolean accepted = false;
		for(int i=0;i<authorizedTps;i++) {
			accepted  = requestCounter.isRequestAuthorized(authorizedTps);
		}
		assertTrue(accepted);
	}
	
	@Test
	public void testUnauthorizedTps() {
		RequestCounter requestCounter = new RequestCounter();
		boolean accepted = false;
		for(int i=0;i<tpsOverflow;i++) {
			accepted  = requestCounter.isRequestAuthorized(authorizedTps);
		}
		assertFalse(accepted);
	}

}
