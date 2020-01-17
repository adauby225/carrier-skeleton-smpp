package com.carrier.smpp.outbound.client;

public class BindTypes {
	private int tranceivers=1;
	private int transmitters=0;
	private int receivers=0;
	
	public BindTypes() {}
	
	public BindTypes(int tranceivers, int transmitters, int receivers) {
		super();
		this.tranceivers = tranceivers;
		this.transmitters = transmitters;
		this.receivers = receivers;
	}
	
	public int getTranceivers() {
		return tranceivers;
	}
	public void setTranceivers(int tranceivers) {
		this.tranceivers = tranceivers;
	}
	public int getTransmitters() {
		return transmitters;
	}
	public void setTransmitters(int transmitters) {
		this.transmitters = transmitters;
	}
	public int getReceivers() {
		return receivers;
	}
	public void setReceivers(int receivers) {
		this.receivers = receivers;
	}

	public int total() {
		return tranceivers + receivers + transmitters;
	}
	
	

}
