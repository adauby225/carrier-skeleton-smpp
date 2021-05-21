package com.carrier.smpp.outbound.client;

import com.carrier.smpp.pdu.request.dispatching.RequestManager;
import com.cloudhopper.smpp.SmppSession;

public interface RequestSender extends Cloneable{

	void send(SmppSession session,RequestManager reqDispatcher, int tps)throws InterruptedException;
	void send(SmppSession session,int requestInterval) throws InterruptedException;
	
	Object clone() throws CloneNotSupportedException;

}