package com.carrier.smpp.pdu;

import java.util.Map;

import com.carrier.smpp.handler.pdu.request.EsmePduRequest;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;
import com.cloudhopper.smpp.pdu.PduResponse;

public interface Handlers {
	Map<Integer, RequestHandler<EsmePduRequest,PduResponse>>getRequestHandlers();
	Map<Integer, ResponseHandler> getResponseHandlers();

}
