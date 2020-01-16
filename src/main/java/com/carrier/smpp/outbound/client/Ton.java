package com.carrier.smpp.outbound.client;

public class Ton {
	private byte destTon=0;
	private byte sourceTon=0;
	
	public Ton() {
		super();
	}

	public Ton(byte destTon, byte sourceTon) {
		super();
		this.destTon = destTon;
		this.sourceTon = sourceTon;
	}

	public byte getDestTon() {
		return destTon;
	}

	public void setDestTon(byte destTon) {
		this.destTon = destTon;
	}

	public byte getSourceTon() {
		return sourceTon;
	}

	public void setSourceTon(byte sourceTon) {
		this.sourceTon = sourceTon;
	}
	
	
	
	

}
