package com.carrier.smpp.outbound.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import com.carrier.smpp.pdu.request.dispatching.RequestCollections;
import com.carrier.smpp.util.RetryCounter;
import com.carrier.smpp.util.SmppConstantExtension;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.tlv.Tlv;

public class ClientSmppSessionHandlerTest {
	
	@Test
	public void testAddExpiredRequestForTheFirstTime() {
		RequestCollections collection = new RequestCollections();
		ClientSmppSessionHandler clientSmppSession = new ClientSmppSessionHandler(collection);
		SubmitSm submit = new SubmitSm();
		clientSmppSession.firePduRequestExpired(submit);
		assertTrue(submit.hasOptionalParameter(SmppConstantExtension.TAG_RETRY));
		Tlv tlvretry = submit.getOptionalParameter(SmppConstantExtension.TAG_RETRY);
		assertEquals(1, collection.sizeOfRequests());
		assertEquals(1, RetryCounter.getRetryCount(tlvretry));
	}
	
	@Test
	public void testAddExpiredRequestHavingReachedMaxRetry() {
		RequestCollections collection = new RequestCollections();
		ClientSmppSessionHandler clientSmppSession = new ClientSmppSessionHandler(collection);
		BigInteger maxRetry = BigInteger.valueOf(2);
		SubmitSm submit = new SubmitSm();
		submit.addOptionalParameter(new Tlv(SmppConstantExtension.TAG_RETRY, maxRetry.toByteArray()));
		clientSmppSession.firePduRequestExpired(submit);
		assertEquals(0, collection.sizeOfRequests());
	}
	
	@Test
	public void testAddExpiredRequestForAnotherRetry() {
		RequestCollections collection = new RequestCollections();
		ClientSmppSessionHandler clientSmppSession = new ClientSmppSessionHandler(collection);
		BigInteger maxRetry = BigInteger.valueOf(1);
		SubmitSm submit = new SubmitSm();
		submit.addOptionalParameter(new Tlv(SmppConstantExtension.TAG_RETRY, maxRetry.toByteArray()));
	
		clientSmppSession.firePduRequestExpired(submit);
		Tlv tlvretry = submit.getOptionalParameter(SmppConstantExtension.TAG_RETRY);
		assertEquals(1, collection.sizeOfRequests());
		assertEquals(2, RetryCounter.getRetryCount(tlvretry));
	}
	
}
