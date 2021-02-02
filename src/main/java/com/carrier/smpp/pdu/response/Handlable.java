package com.carrier.smpp.pdu.response;

public interface Handlable<T> {
	void handle(T t);

}
