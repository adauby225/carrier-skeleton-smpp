package com.carrier.smpp.pdu.request.dispatching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.SubmitSm;

public class RequestDispatcherTest {


	@Test
	public void testEmptyRequestDispatcher() {
		RequestCollections reqDispatcher = new RequestCollections();
		assertEquals(0, reqDispatcher.sizeOfRequests());

	}
	@Test
	public void testAddOneRequest() {
		RequestCollections reqDispatcher = new RequestCollections();
		reqDispatcher.addRequest(new SubmitSm());
		assertEquals(1, reqDispatcher.sizeOfRequests());
	}

	@Test
	public void testAddManyRequest() {
		RequestCollections reqDispatcher = new RequestCollections();
		for(int i=0;i<20000;i++)
			reqDispatcher.addRequest(new SubmitSm());
		assertEquals(20000, reqDispatcher.sizeOfRequests());
	}

	@Test
	public void testGetNextRequest() {
		RequestCollections reqDispatcher = new RequestCollections();
		for(int i=0;i<2;i++)
			reqDispatcher.addRequest(new SubmitSm());
		Optional<PduRequest> request = reqDispatcher.nextRequest();
		assertTrue(request.isPresent());
	}

	@Test
	public void testNextRequestFromEmptyCollections() {
		RequestCollections reqDispatcher = new RequestCollections();
		Optional<PduRequest> request = reqDispatcher.nextRequest();
		assertFalse(request.isPresent());
	}


}
