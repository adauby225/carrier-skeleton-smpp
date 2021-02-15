package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_INVPASWD;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVSYSID;

import com.carrier.smpp.model.SmppAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultCredentialChecker implements SmppAccountParamChecker{

	@Override
	public int check(SmppSessionConfiguration sessionConfiguration, SmppAccount smppAccount) {
		String systemId = sessionConfiguration.getSystemId();
		String password = sessionConfiguration.getPassword();
		if(!systemId.equals(smppAccount.getSystemId()))
			return STATUS_INVSYSID;
		if(!password.equals(smppAccount.getPassword()))
			return STATUS_INVPASWD;
		return 0;
	}

}
