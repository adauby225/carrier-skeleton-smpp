package com.carrier.smpp.demo.server;

import com.carrier.smpp.demo.EsmeSmppAccount;
import com.carrier.smpp.model.SmppAccount;
import com.carrier.smpp.model.SmppAccountRepository;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class EsmeAccountRepositoryExple implements SmppAccountRepository<SmppSessionConfiguration> {

	@Override
	public SmppAccount find(SmppSessionConfiguration sessionConfig) {
		return new EsmeSmppAccount();
	}

}
