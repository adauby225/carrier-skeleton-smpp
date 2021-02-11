package com.carrier.smpp.handler.pdu.response;

public interface ResponseHandler<T> {
	void handleResponse(T t);

}
