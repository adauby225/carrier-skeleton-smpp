package com.carrier.smpp.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import com.cloudhopper.smpp.tlv.Tlv;

public class RetryCounterTest {
	@Test
	public void testIsMaxRetryReached() {
		BigInteger maxRetry = BigInteger.valueOf(2);
		assertTrue(RetryCounter.isMaxRetryCountReached(new Tlv(SmppConstantExtension.TAG_RETRY, maxRetry.toByteArray())));
	}
	
	@Test
	public void testUnreachedMaxRetry() {
		BigInteger maxRetry = BigInteger.valueOf(1);
		assertFalse(RetryCounter.isMaxRetryCountReached(new Tlv(SmppConstantExtension.TAG_RETRY, maxRetry.toByteArray())));
		
	}

}
