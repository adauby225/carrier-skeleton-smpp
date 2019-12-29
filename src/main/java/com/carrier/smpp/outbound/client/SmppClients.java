package com.carrier.smpp.outbound.client;

import java.util.HashMap;
import java.util.Map;

public class SmppClients {
	private static SmppClients instance;
	private Map<Long,Object>clients = new HashMap<>();
	private SmppClients() {}



	public static SmppClients getInstance() {
		if(instance == null) {
			synchronized(SmppClients.class) {
				if(instance == null) {
					instance = new SmppClients();
				}
			}
		}
		return instance;
	}

	public void startAll() {


	}
}
