package com.carrier.smpp.handler.pdu.response;

import com.cloudhopper.smpp.PduAsyncResponse;

public class HandlingResponseTask implements Runnable {
	private final ResponseHandler<PduAsyncResponse> respHandler;
	private PduAsyncResponse asyncResponse;
	public HandlingResponseTask(ResponseHandler<PduAsyncResponse> respHandler
			, PduAsyncResponse asyncResponse) {
		this.respHandler = respHandler;
		this.asyncResponse = asyncResponse;
	}

	@Override
	public void run() {
		respHandler.handleResponse(asyncResponse);
	}

}
