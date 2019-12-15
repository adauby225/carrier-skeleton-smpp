package com.carrier.smpp.esme.response;

import com.cloudhopper.smpp.PduAsyncResponse;

public interface EsmeResponseHandler {

	void handleResponse(PduAsyncResponse pduAsyncResponse);

}
