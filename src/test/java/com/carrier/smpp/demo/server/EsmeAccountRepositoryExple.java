package com.carrier.smpp.demo.server;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.carrier.smpp.model.esme.EsmeAccountRepository;

public class EsmeAccountRepositoryExple implements EsmeAccountRepository {

	@Override
	public EsmeSmppAccount findBySystemId(String systemId) {
		return new EsmeSmppAccount();
	}

}
