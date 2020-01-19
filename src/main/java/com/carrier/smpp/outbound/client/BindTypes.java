package com.carrier.smpp.outbound.client;

public class BindTypes {
	private int transceivers=1;
	private int transmitters=0;
	private int receivers=0;
	
	public BindTypes() {}
	
	public BindTypes(int transceivers, int transmitters, int receivers) {
		super();
		this.transceivers = transceivers;
		this.transmitters = transmitters;
		this.receivers = receivers;
	}
	
	public int getTransceivers() {
		return transceivers;
	}
	public void setTransceivers(int transceivers) {
		this.transceivers = transceivers;
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
		return transceivers + receivers + transmitters;
	}

	public void update(BindTypes bindTypes) {
		transceivers = transceivers + bindTypes.getTransceivers();
		transmitters = transmitters + bindTypes.getTransmitters();
		receivers = receivers + bindTypes.getReceivers();
	}

	
	

}
