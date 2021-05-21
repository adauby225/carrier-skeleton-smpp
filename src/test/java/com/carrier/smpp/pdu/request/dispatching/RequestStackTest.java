package com.carrier.smpp.pdu.request.dispatching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.SubmitSm;

public class RequestStackTest {
	
	
	@Test
	public void testAddOneRequest() {
		RequestStack stack = new RequestStack();
		stack.addRequest(new SubmitSm());
		assertEquals(1, stack.size());
	}
	@Test
	public void testAddManyRequests() {
		RequestStack stack = new RequestStack();
		for(int i=0;i<10000;i++)
			stack.addRequest(new SubmitSm());
		assertEquals(10000, stack.size());
	}
	
	@Test
	public void testEmptyStack() {
		RequestStack stack = new RequestStack();
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void testTakeNextRequest() {
		RequestStack stack = new RequestStack();
		for(int i=0;i<10000;i++)
			stack.addRequest(new SubmitSm());
		
		PduRequest request = stack.nextRequest();
		assertEquals(9999, stack.size());
		assertNotNull(request);
	}
	
	@Test
	public void testTakeNextRequestFromEmptyStack() {
		RequestStack stack = new RequestStack();
		PduRequest request = stack.nextRequest();
		assertNull(request);
	}
	

}
