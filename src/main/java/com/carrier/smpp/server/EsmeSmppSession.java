package com.carrier.smpp.server;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.smpp.SmppSession;

public class EsmeSmppSession {
	private static final long DEFAULT_UNBIND_TIMEOUT = 1000;
	private final SmppSession smppSession;
	private long unbindTimeout=DEFAULT_UNBIND_TIMEOUT;
	private long id;
	private EsmeSmppAccount account;
	public EsmeSmppSession(long id, SmppSession smppSession, EsmeSmppAccount account,long unbindTimeout) {
		this.id = id;
		this.smppSession = smppSession;
		this.account = account;
		this.unbindTimeout = unbindTimeout;
	}

	public EsmeSmppSession(long id, SmppSession smppSession,EsmeSmppAccount account) {
		this.id = id;
		this.smppSession = smppSession;
		this.account = account;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SmppSession getSmppSession() {
		return smppSession;
	}


	public EsmeSmppAccount getAccount() {
		return account;
	}

	public void destroySession() {
		if(smppSession!=null) {
			smppSession.unbind(unbindTimeout);
			smppSession.destroy();
		}

	}	



}
