package com.carrier.smpp.model.esme;


public class EsmeSmppAccount {
	private Long id;
	protected String systemId;
	protected String password;
	protected String host;
	
	
	public EsmeSmppAccount(Long id, String systemId, String password) {
		this.id = id;
		this.systemId = systemId;
		this.password = password;
	}
	
	public EsmeSmppAccount(String systemId, String password) {
		this.systemId = systemId;
		this.password = password;
	}

	public EsmeSmppAccount() {} 
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
