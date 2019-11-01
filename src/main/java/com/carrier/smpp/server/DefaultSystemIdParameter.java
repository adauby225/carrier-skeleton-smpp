package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_INVSYSID;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import com.carrier.smpp.model.esme.EsmeSessionConfigParams;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultSystemIdParameter implements ConfigParameter {

	@Override
	public int check(SmppSessionConfiguration sessionConfiguration, EsmeSessionConfigParams configParams) {
		String systemId = sessionConfiguration.getSystemId();
		if(!systemId.equals(configParams.getSystemId()))
			return STATUS_INVSYSID;
		return STATUS_OK;
	}

}