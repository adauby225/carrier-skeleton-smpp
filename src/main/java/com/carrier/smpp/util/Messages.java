package com.carrier.smpp.util;

public class Messages {

	public static final String NULL_REQUEST_HANDLER = "Request handlers cannot be null";
	public static final String NULL_RESPONSE_HANDLER = "Response handlers cannot be null";
	public static final String UNBINDING = "Unbinding . . .";
	public static final String UNAUTHORIZED_OPERATION ="Unauthorized operation";
	public static final String SENDING_UNBIND = "Sending unbind request. . .";
	public static final String SESSION_DESTRUCTION = "Destroying session . . .";
	public static final String DESTROYED_SESSION = "Session destroyed . . .";
	private Messages() {
		throw new UnsupportedOperationException("Unsupported operation");
	}
}
