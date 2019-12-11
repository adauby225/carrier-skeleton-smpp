package com.carrier.smpp.esme.request;

import com.carrier.smpp.server.EsmeSmppSession;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public interface EsmeRequestHandler {

	PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession);

}
