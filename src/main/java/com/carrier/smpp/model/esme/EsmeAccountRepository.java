package com.carrier.smpp.model.esme;

public interface EsmeAccountRepository {

	EsmeSmppAccount findBySystemId(String systemId);

}
