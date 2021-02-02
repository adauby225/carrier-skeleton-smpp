package com.carrier.smpp.outbound.client;

public class DefaultMaxTpsCalculator implements MaxRequestPerSecond{

	@Override
	public int calculateTpsByBind(BindTypes bindTypes, int tps) {
		if(tps >0 && bindTypes.total()<tps)
			return (tps/bindTypes.total()) ;
		return 1;
	}

	

}
