package com.carrier.smpp.outbound.client;

public class Npi {
	private byte destNpi = 0;
	private byte sourceNpi = 0;
	
	public Npi() {}
	
	public Npi(byte destNpi, byte sourceNpi) {
		super();
		this.destNpi = destNpi;
		this.sourceNpi = sourceNpi;
	}
	public byte getDestNpi() {
		return destNpi;
	}
	public void setDestNpi(byte destNpi) {
		this.destNpi = destNpi;
	}
	public byte getSourceNpi() {
		return sourceNpi;
	}
	public void setSourceNpi(byte sourceNpi) {
		this.sourceNpi = sourceNpi;
	}
	
	

}
