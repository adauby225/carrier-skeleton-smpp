package com.carrier.smpp.handler.pdu.request;

import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import java.util.List;

import com.carrier.smpp.model.SmppAccount;
import com.carrier.smpp.model.SmppAccountRepository;
import com.carrier.smpp.server.SmppAccountParamChecker;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultEsmeBindRequestHandler implements RequestHandler<SmppSessionConfiguration, Integer> {
	private final List<SmppAccountParamChecker>parameters;
	private final SmppAccountRepository<SmppSessionConfiguration> esmeAccountRepository;
	public DefaultEsmeBindRequestHandler(List<SmppAccountParamChecker> parameters
			, SmppAccountRepository<SmppSessionConfiguration> esmeAccountRepository) {
		super();
		this.parameters = parameters;
		this.esmeAccountRepository = esmeAccountRepository;
	}

	@Override
	public Integer handleRequest(SmppSessionConfiguration sessionConfiguration) {
		SmppAccount configParams = esmeAccountRepository.find(sessionConfiguration);
		for(SmppAccountParamChecker param : parameters) {
			int status = param.check(sessionConfiguration,configParams); 
			if(status !=STATUS_OK)
				return status;
		}
		return STATUS_OK;
	}

}
