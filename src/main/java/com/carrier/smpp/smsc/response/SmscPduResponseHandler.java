package com.carrier.smpp.smsc.response;

import com.cloudhopper.smpp.PduAsyncResponse;

public interface SmscPduResponseHandler {

	void handleResponse(PduAsyncResponse pduAsyncResponse);
	
}
