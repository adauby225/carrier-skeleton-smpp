package com.carrier.smpp.model.esme;


public class EsmeAccount {
	private String systemId;
	private String password;
	private String host;
	
	
	
	public EsmeAccount(String systemId, String password) {
		super();
		this.systemId = systemId;
		this.password = password;
	}

	public EsmeAccount() {} 
	
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	

	

}
