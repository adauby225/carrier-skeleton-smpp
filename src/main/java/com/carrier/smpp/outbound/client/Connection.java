package com.carrier.smpp.outbound.client;

import com.cloudhopper.smpp.SmppBindType;

public interface Connection <T> {

	void establishBind(T t,PduQueue pduQueue,SmppBindType bindType,int tps) throws CloneNotSupportedException;
	void establishBind(T t,PduQueue pduQueue,int tps);

}
