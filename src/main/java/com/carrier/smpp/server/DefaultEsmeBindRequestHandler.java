package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import java.util.List;

import com.carrier.smpp.model.esme.EsmeAccountRepository;
import com.carrier.smpp.model.esme.EsmeAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultEsmeBindRequestHandler implements BindRequestHandler {
	private final List<ConfigParameter>parameters;
	private final EsmeAccountRepository configParamsRepository;
	public DefaultEsmeBindRequestHandler(List<ConfigParameter> parameters, EsmeAccountRepository configParamsRepository) {
		super();
		this.parameters = parameters;
		this.configParamsRepository = configParamsRepository;
	}

	@Override
	public int handleRequest(SmppSessionConfiguration sessionConfiguration) {
		EsmeAccount configParams = configParamsRepository.findBySystemId();
		for(ConfigParameter param : parameters) {
			int status = param.check(sessionConfiguration,configParams); 
			if(status !=STATUS_OK)
				return status;
		}
		return STATUS_OK;
	}

}
