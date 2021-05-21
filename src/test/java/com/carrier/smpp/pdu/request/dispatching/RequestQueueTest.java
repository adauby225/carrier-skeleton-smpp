package com.carrier.smpp.pdu.request.dispatching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.SubmitSm;

public class RequestQueueTest {
	@Test
	public void testEmptyQueue() {
		RequestQueue reqQueue = new RequestQueue();
		assertTrue(reqQueue.isEmpty());
	}
	
	@Test
	public void testAddOneRequest() {
		RequestQueue reqQueue = new RequestQueue();
		reqQueue.addRequest(new SubmitSm());
		assertFalse(reqQueue.isEmpty());
		assertEquals(1,reqQueue.size());
	}
	
	@Test
	public void testAddManyRequest() {
		RequestQueue reqQueue = new RequestQueue();
		for(int i=0;i<50000;i++)
			reqQueue.addRequest(new SubmitSm());
		assertEquals(50000,reqQueue.size());
	}
	
	@Test
	public void testTakeNextRequest() {
		RequestQueue reqQueue = new RequestQueue();
		for(int i=0;i<50000;i++) 
			reqQueue.addRequest(new SubmitSm());
		PduRequest request = reqQueue.nextRequest();
		assertEquals(49999,reqQueue.size());
		assertNotNull(request);
	}
	
	@Test
	public void testTakeNextRequestFromEmptyStack() {
		RequestQueue reqQueue = new RequestQueue();
		PduRequest request = reqQueue.nextRequest();
		assertNull(request);
	}

}
