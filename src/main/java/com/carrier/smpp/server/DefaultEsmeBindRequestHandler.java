package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import java.util.List;

import com.carrier.smpp.model.esme.ConfigParamsRepository;
import com.carrier.smpp.model.esme.EsmeSessionConfigParams;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultEsmeBindRequestHandler implements BindRequestHandler {
	private final List<ConfigParameter>parameters;
	private final ConfigParamsRepository configParamsRepository;
	public DefaultEsmeBindRequestHandler(List<ConfigParameter> parameters, ConfigParamsRepository configParamsRepository) {
		super();
		this.parameters = parameters;
		this.configParamsRepository = configParamsRepository;
	}

	@Override
	public int handleRequest(SmppSessionConfiguration sessionConfiguration) {
		EsmeSessionConfigParams configParams = configParamsRepository.findBySystemId();
		for(ConfigParameter param : parameters) {
			int status = param.check(sessionConfiguration,configParams); 
			if(status !=STATUS_OK)
				return status;
		}
		return STATUS_OK;
	}

}
