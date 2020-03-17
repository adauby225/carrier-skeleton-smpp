package com.carrier.smpp.esme.request;

import java.util.Map;

import com.carrier.smpp.esme.response.EsmeResponseHandler;

public interface Handlers {
	Map<Integer, EsmeRequestHandler>getrequestHandlers();
	Map<Integer, EsmeResponseHandler> getResponseHandlers();

}
