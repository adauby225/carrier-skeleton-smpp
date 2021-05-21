package com.carrier.smpp.pdu.request.dispatching;

import java.util.Optional;

import com.cloudhopper.smpp.pdu.PduRequest;

public interface RequestManager {

	public void addRequest(PduRequest request) ;
	public Optional<PduRequest> nextRequest();
	public long sizeOfRequests();
}
