package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_INVPASWD;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import com.carrier.smpp.model.esme.EsmeSessionConfigParams;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultPasswordParameter implements ConfigParameter {

	@Override
	public int check(SmppSessionConfiguration sessionConfiguration, EsmeSessionConfigParams configParams) {
		String password = sessionConfiguration.getPassword();
		if(!password.equals(configParams.getPassword()))
			return STATUS_INVPASWD;
		return STATUS_OK;
	}

}
