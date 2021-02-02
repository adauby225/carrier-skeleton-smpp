package com.carrier.smpp.esme.request;

import java.util.Map;

import com.carrier.smpp.esme.response.EsmeResponseHandler;
import com.carrier.smpp.pdu.Handler.PduRequestHandler;

public interface Handlers {
	Map<Integer, PduRequestHandler>getrequestHandlers();
	Map<Integer, EsmeResponseHandler> getResponseHandlers();

}
