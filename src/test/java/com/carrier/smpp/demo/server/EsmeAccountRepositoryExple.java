package com.carrier.smpp.demo.server;

import com.carrier.smpp.model.esme.EsmeAccount;
import com.carrier.smpp.model.esme.EsmeAccountRepository;

public class EsmeAccountRepositoryExple implements EsmeAccountRepository {

	@Override
	public EsmeAccount findBySystemId(String systemId) {
		return new EsmeAccount();
	}

}
