package com.carrier.smpp.handler.pdu.request;

import com.cloudhopper.smpp.pdu.PduResponse;

public interface RequestHandler<T> {
	PduResponse handleRequest(T t);
}
