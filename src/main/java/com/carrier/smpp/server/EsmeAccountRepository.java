package com.carrier.smpp.server;

import com.carrier.smpp.model.esme.EsmeAccount;

public interface EsmeAccountRepository {

	EsmeAccount findBySystemId(String systemId);

}
