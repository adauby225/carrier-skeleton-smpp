package com.carrier.smpp.pdu.Handler;

public interface PduRespHandler<T> {
	void handle(T t);

}
