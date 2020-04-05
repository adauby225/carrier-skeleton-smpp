package com.carrier.smpp.server;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class DefaultBindMaxChecker implements SmppAccountParamCheckable {
	private SessionManager sessionManger = SessionManager.getInstance();
	@Override
	public int check(SmppSessionConfiguration sessionConfiguration, EsmeSmppAccount smppAccount) {
		long existingSessionCount = sessionManger.countSessions(sessionConfiguration.getSystemId());
		if(existingSessionCount == smppAccount.getBindMax())
			return SmppConstants.STATUS_BINDFAIL;
		return SmppConstants.STATUS_OK;
	}

}
