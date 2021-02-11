package com.carrier.smpp.handler.pdu.request;

import com.carrier.smpp.server.EsmeSmppSession;
import com.cloudhopper.smpp.pdu.PduRequest;

public class EsmePduRequest {
	private PduRequest request;
	private EsmeSmppSession esmeSmppSession;
	public EsmePduRequest(PduRequest request, EsmeSmppSession esmeSmppSession) {
		super();
		this.request = request;
		this.esmeSmppSession = esmeSmppSession;
	}
	public PduRequest getRequest() {
		return request;
	}
	public void setRequest(PduRequest request) {
		this.request = request;
	}
	public EsmeSmppSession getEsmeSmppSession() {
		return esmeSmppSession;
	}
	public void setEsmeSmppSession(EsmeSmppSession esmeSmppSession) {
		this.esmeSmppSession = esmeSmppSession;
	}
	
	

}
