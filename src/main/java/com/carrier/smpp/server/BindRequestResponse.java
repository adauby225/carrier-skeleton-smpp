package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

public class BindRequestResponse<T> {
	private int responseStatus=STATUS_OK;
	private T esmeAccount;
	public BindRequestResponse(int responseStatus) {
		this.responseStatus = responseStatus;
	}
	public BindRequestResponse() {}
	public int getResponseStatus() {
		return responseStatus;
	}
	
	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}
	public T getEsmeAccount() {
		return esmeAccount;
	}
	
	public void setEsmeAccount(T esmeAccount) {
		this.esmeAccount = esmeAccount;
	}
	
	
}
