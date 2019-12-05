package com.carrier.smpp.server;

import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;

public class EsmeSmppSessionHandler extends DefaultSmppSessionHandler {
	private final Long sessionId;
	public EsmeSmppSessionHandler(Long sessionId, EsmeSmppSession esmeSmppSession) {
		this.sessionId = sessionId;
	}
	public Long getSessionId() {
		return sessionId;
	}
	
	

}
