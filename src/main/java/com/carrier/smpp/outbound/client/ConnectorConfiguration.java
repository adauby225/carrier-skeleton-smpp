package com.carrier.smpp.outbound.client;

import static com.cloudhopper.smpp.SmppConstants.DEFAULT_WINDOW_SIZE;

import com.cloudhopper.smpp.SmppConstants;

public class ConnectorConfiguration{
	private static final int DEFAULT_TPS = 1;
	
	private String name;
	private String login;
	private String password;
	private int windowSize = DEFAULT_WINDOW_SIZE;
	private String host;
	private String remoteHost;
	private int remotePort;
	private Npi npi = new Npi();
	private Ton ton = new Ton();
	/*
	 * the value  of Enquire link interval is in milliseconds
	 */
	private long enquireLinkInterval = 15000;
	private int throughput=DEFAULT_TPS;
	private byte smppVersion = SmppConstants.VERSION_3_4;
	private String systemType;
	private BindTypes bindTypes=new BindTypes();
	
	public ConnectorConfiguration() {}
	public ConnectorConfiguration(String name,String login, String password, String remoteHost
			, int remotePort) {
		this(login, password,  remoteHost, remotePort);
		this.name = name;
	}
	
	public ConnectorConfiguration(String login, String password, String remoteHost, int remotePort) {
		super();
		this.login = login;
		this.password = password;
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
	}
	
	public ConnectorConfiguration(String name, String login, String password, String host,
			String remoteHost, int remotePort) {
		this(login, password,  remoteHost, remotePort);
		this.name = name;
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public Npi getNpi() {
		return npi;
	}

	public void setNpi(Npi npi) {
		this.npi = npi;
	}

	public Ton getTon() {
		return ton;
	}

	public void setTon(Ton ton) {
		this.ton = ton;
	}

	public long getEnquireLinkInterval() {
		return enquireLinkInterval;
	}

	public void setEnquireLinkInterval(long enquireLinkInterval) {
		this.enquireLinkInterval = enquireLinkInterval;
	}

	public int getThroughput() {
		return throughput;
	}

	public void setThroughput(int throughput) {
		this.throughput = throughput;
	}
	
	public byte getSmppVersion() {
		return smppVersion;
	}
	
	public void setSmppVersion(byte smppVersion) {
		this.smppVersion = smppVersion;
	}
	
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public boolean isHostEmpty() {
		return host==null;
	}
	public BindTypes getBindTypes() {
		return bindTypes;
	}
	public void setBindTypes(BindTypes bindTypes) {
		this.bindTypes = bindTypes;
	}
	public void updateBindTypes(BindTypes bindTypes) {
		this.bindTypes.update(bindTypes);
	}
	
	
	
	
	
	
}
