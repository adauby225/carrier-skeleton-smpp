package com.carrier.smpp.server;

import com.carrier.smpp.model.esme.EsmeAccount;
import com.cloudhopper.smpp.SmppSession;

public class EsmeSmppSession {
	private static final long DEFAULT_UNBIND_TIMEOUT = 1000;
	private final SmppSession smppSession;
	private long unbindTimeout=DEFAULT_UNBIND_TIMEOUT;
	private EsmeAccount account;
	public EsmeSmppSession(SmppSession smppSession, EsmeAccount account,long unbindTimeout) {
		this.smppSession = smppSession;
		this.account = account;
		this.unbindTimeout = unbindTimeout;
	}

	public EsmeSmppSession(SmppSession smppSession,EsmeAccount account) {
		this.smppSession=smppSession;
		this.account = account;
	}

	public SmppSession getSmppSession() {
		return smppSession;
	}


	public EsmeAccount getAccount() {
		return account;
	}

	public void destroySession() {
		smppSession.unbind(unbindTimeout);
		smppSession.destroy();

	}	



}
