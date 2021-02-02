package com.carrier.smpp.pdu.Handler;

import com.cloudhopper.smpp.pdu.PduResponse;

public interface PduRequestHandler<T> {
	PduResponse handleRequest(T t);
}
