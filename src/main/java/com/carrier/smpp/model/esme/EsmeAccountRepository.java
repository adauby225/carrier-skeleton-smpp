package com.carrier.smpp.model.esme;

public interface EsmeAccountRepository {

	EsmeAccount findBySystemId(String systemId);

}
