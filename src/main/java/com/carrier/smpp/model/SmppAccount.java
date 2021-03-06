package com.carrier.smpp.model;

import com.carrier.smpp.util.Values;

public abstract class SmppAccount {
	private Long id;
	private String name;
	protected String systemId;
	protected String password;
	protected String host;
	private int bindMax;
	private int tps=Values.DEFAULT_TPS;
	
	
	public SmppAccount(Long id, String systemId, String password) {
		this.id = id;
		this.systemId = systemId;
		this.password = password;
	}
	
	public SmppAccount(String systemId, String password) {
		this.systemId = systemId;
		this.password = password;
	}
	
	public SmppAccount(String systemId, String password,int bindMax) {
		this.systemId = systemId;
		this.password = password;
		this.bindMax = bindMax;
	}

	public SmppAccount() {} 
	
	
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
	public void setBindMax(int bindMax) {
		this.bindMax = bindMax;
	}
	public int getBindMax() {
		return bindMax;
	}
	

	public int getTps() {
		return tps;
	}

	public void setTps(int tps) {
		this.tps = tps;
	}

	
	public String getName() {
		if(name==null)return systemId;
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean systemIdMatches(String systemId) {
		return systemId.matches(systemId);
	}
	

}
