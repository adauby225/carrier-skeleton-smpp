package com.carrier.smpp.util;

public class Messages {

	public static final String NULL_REQUEST_HANDLER = "Request handlers cannot be null";
	public static final String NULL_RESPONSE_HANDLER = "Response handlers cannot be null";
	public static final String UNBINDING = "Unbinding. . .";
	public static final String UNAUTHORIZED_OPERATION ="Unauthorized operation";
	private Messages() {
		throw new UnsupportedOperationException("Unsupported operation");
	}
}
