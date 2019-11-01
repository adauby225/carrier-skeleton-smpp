package com.carrier.smpp.demo.server;

import static com.carrier.smpp.demo.server.DemoConstants.PASSWORD;
import static com.carrier.smpp.demo.server.DemoConstants.SYSTEM_ID;

import com.carrier.smpp.model.esme.ConfigParamsRepository;
import com.carrier.smpp.model.esme.EsmeSessionConfigParams;

public class ConfigParamsRepositoryExple implements ConfigParamsRepository {

	@Override
	public EsmeSessionConfigParams findBySystemId() {
		return new EsmeSessionConfigParams(SYSTEM_ID, PASSWORD);
	}

}
