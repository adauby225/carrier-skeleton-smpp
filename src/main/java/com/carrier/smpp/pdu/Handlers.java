package com.carrier.smpp.pdu;

import java.util.Map;

import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.handler.pdu.response.ResponseHandler;

public interface Handlers {
	Map<Integer, RequestHandler>getrequestHandlers();
	Map<Integer, ResponseHandler> getResponseHandlers();

}
