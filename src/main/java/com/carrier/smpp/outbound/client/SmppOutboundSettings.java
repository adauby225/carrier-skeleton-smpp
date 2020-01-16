package com.carrier.smpp.outbound.client;

import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public class SmppOutboundSettings extends SmppSessionConfiguration{
	private static final int DEFAULT_TPS = 1;
	private Npi npi;
	private Ton ton;
	private long enquireLinkInterval;
	private int throughput=DEFAULT_TPS;
	/*private int receivers=1;
	private int transmitters=1;
	private int transceivers=0;*/
	
	public SmppOutboundSettings() {
		super();
	}
	
	public SmppOutboundSettings(Npi npi, Ton ton, long enquireLinkInterval, int throughput) {
		super();
		this.npi = npi;
		this.ton = ton;
		this.enquireLinkInterval = enquireLinkInterval;
		this.throughput = throughput;
	}
	public SmppOutboundSettings(String systemId, String password) {
		super();
		setSystemId(systemId);
		setPassword(password);
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
	/*public int getReceivers() {
		return receivers;
	}

	public int getTransmitters() {
		return transmitters;
	}

	public void setTransmitters(int transmitters) {
		this.transmitters = transmitters;
	}

	public int getTransceivers() {
		return transceivers;
	}

	public void setTransceivers(int transceivers) {
		this.transceivers = transceivers;
	}

	public void setReceivers(int receivers) {
		this.receivers = receivers;
	}*/
}
