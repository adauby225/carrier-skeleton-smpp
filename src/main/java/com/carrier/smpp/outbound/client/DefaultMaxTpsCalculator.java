package com.carrier.smpp.outbound.client;

import com.carrier.smpp.handler.pdu.request.MaxRequestPerSecond;

public class DefaultMaxTpsCalculator implements MaxRequestPerSecond{

	@Override
	public int calculateTpsByBind(BindTypes bindTypes, int tps) {
		if(tps >0 && bindTypes.total()<tps)
			return (tps/bindTypes.total()) ;
		return 1;
	}

	

}
