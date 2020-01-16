package com.carrier.smpp.outbound.client;

import com.cloudhopper.smpp.SmppBindType;

public interface Connection <T> {
	/* ConcurrentMap<Long, T> runConnectionWorkers(SmppOutboundSettings settings,PduQueue pduQueue);
	T runConnectionWorker(SmppOutboundSettings settings,PduQueue pduQueue);
	T runConnectionWorker(SmppOutboundSettings settings,SmppBindType bindType,PduQueue pduQueue);*/
	void establishBind(T t,PduQueue pduQueue,SmppBindType bindType);

}
