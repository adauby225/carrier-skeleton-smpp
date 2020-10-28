package com.carrier.smpp.esme.response;

import com.cloudhopper.smpp.PduAsyncResponse;

public class DefaultResponseHandler implements EsmeResponseHandler {
	

	public DefaultResponseHandler() {
		super();
	}

	@Override
	public void handleResponse(PduAsyncResponse pduAsyncResponse) {
		pduAsyncResponse.getResponse();
	}

}
