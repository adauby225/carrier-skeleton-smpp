package com.carrier.smpp.handler.pdu.request;

public interface RequestHandler<T,R> {
	R handleRequest(T t);
}
