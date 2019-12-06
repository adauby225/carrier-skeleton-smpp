package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import java.util.List;

import com.carrier.smpp.model.esme.EsmeAccountRepository;
import com.carrier.smpp.model.esme.EsmeAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultEsmeBindRequestHandler implements BindRequestHandler {
	private final List<ConfigParameter>parameters;
	private final EsmeAccountRepository esmeAccountRepository;
	public DefaultEsmeBindRequestHandler(List<ConfigParameter> parameters, EsmeAccountRepository esmeAccountRepository) {
		super();
		this.parameters = parameters;
		this.esmeAccountRepository = esmeAccountRepository;
	}

	@Override
	public int handleRequest(SmppSessionConfiguration sessionConfiguration) {
		EsmeAccount configParams = esmeAccountRepository.findBySystemId(sessionConfiguration.getSystemId());
		for(ConfigParameter param : parameters) {
			int status = param.check(sessionConfiguration,configParams); 
			if(status !=STATUS_OK)
				return status;
		}
		return STATUS_OK;
	}

}
