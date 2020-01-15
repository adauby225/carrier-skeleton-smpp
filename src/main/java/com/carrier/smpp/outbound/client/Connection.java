package com.carrier.smpp.outbound.client;

public interface Connection <T,R> {
	/* ConcurrentMap<Long, T> runConnectionWorkers(SmppOutboundSettings settings,PduQueue pduQueue);
	T runConnectionWorker(SmppOutboundSettings settings,PduQueue pduQueue);
	T runConnectionWorker(SmppOutboundSettings settings,SmppBindType bindType,PduQueue pduQueue);*/
	R establishBind(T t,PduQueue pduQueue);

}
