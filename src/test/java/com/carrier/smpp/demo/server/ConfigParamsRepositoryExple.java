package com.carrier.smpp.demo.server;

import static com.carrier.smpp.demo.server.DemoConstants.PASSWORD;
import static com.carrier.smpp.demo.server.DemoConstants.SYSTEM_ID;

import com.carrier.smpp.model.esme.EsmeAccountRepository;
import com.carrier.smpp.model.esme.EsmeAccount;

public class ConfigParamsRepositoryExple implements EsmeAccountRepository {

	@Override
	public EsmeAccount findBySystemId() {
		return new EsmeAccount(SYSTEM_ID, PASSWORD);
	}

}
