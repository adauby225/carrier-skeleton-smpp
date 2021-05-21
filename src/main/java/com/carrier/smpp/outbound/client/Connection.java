package com.carrier.smpp.outbound.client;

import com.carrier.smpp.pdu.request.dispatching.RequestManager;
import com.cloudhopper.smpp.SmppBindType;

public interface Connection <T> {

	void establishBind(T t,RequestManager reqDispatching,SmppBindType bindType,int tps) throws CloneNotSupportedException;
	void establishBind(T t,RequestManager reqDispatching,int tps);

}
