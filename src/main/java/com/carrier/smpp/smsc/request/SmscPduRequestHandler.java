package com.carrier.smpp.smsc.request;

import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;

public interface SmscPduRequestHandler {

	PduResponse handle(PduRequest pduRequest);

}
