package com.carrier.smpp.demo;

import com.carrier.smpp.model.SmppAccount;

public class EsmeSmppAccount extends SmppAccount {

	public EsmeSmppAccount() {
		super();
	}

	public EsmeSmppAccount(Long id, String systemId, String password) {
		super(id, systemId, password);
	}

	public EsmeSmppAccount(String systemId, String password, int bindMax) {
		super(systemId, password, bindMax);
	}

	public EsmeSmppAccount(String systemId, String password) {
		super(systemId, password);
	
	}
	

}
