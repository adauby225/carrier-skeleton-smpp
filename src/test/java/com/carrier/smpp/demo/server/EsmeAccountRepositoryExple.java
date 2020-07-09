package com.carrier.smpp.demo.server;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.carrier.smpp.model.esme.EsmeAccountRepository;

public class EsmeAccountRepositoryExple implements EsmeAccountRepository {

	@Override
	public EsmeSmppAccount find(SmppSessionConfiguration sessionConfig) {
		return new EsmeSmppAccount();
	}

}
