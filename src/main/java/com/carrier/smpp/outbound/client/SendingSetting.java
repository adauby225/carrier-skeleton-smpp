package com.carrier.smpp.outbound.client;

public class SendingSetting {
	private int tps=1;
	private Npi npi;
	private Ton ton;
	
	public SendingSetting() {
		
	}
	
	public int getTps() {
		return tps;
	}
	public void setTps(int tps) {
		this.tps = tps;
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
}
