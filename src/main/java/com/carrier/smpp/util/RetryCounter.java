package com.carrier.smpp.util;

import java.math.BigInteger;

import com.cloudhopper.smpp.tlv.Tlv;

public final class RetryCounter {
	public static final short RETRY_MAX = 0x02;
	public static boolean isMaxRetryCountReached(Tlv tvlRetryCount) {
		short retryCount = getRetryCount(tvlRetryCount);
		return retryCount >= RETRY_MAX;
	}

	public static byte[] incrementRetryCount(Tlv tvlRetryCount) {
		short retryCount = getRetryCount(tvlRetryCount);
		BigInteger count =  BigInteger.valueOf((int)retryCount+1);
		return count.toByteArray();
	}

	public static short getRetryCount(Tlv tvlRetryCount) {
		int intValue = 0;
		for (byte b : tvlRetryCount.getValue()) 
		    intValue = (intValue << 8) + (b & 0xFF);
		
		return (short)intValue;

	}
	
	/*public void convertToByteArray() {
		byte[] bytes = new byte[Integer.BYTES];
		int length = bytes.length;
		for (int i = 0; i < length; i++) {
		    bytes[length - i - 1] = (byte) (value & 0xFF);
		    value >>= 8;
		}
	}*/

}
