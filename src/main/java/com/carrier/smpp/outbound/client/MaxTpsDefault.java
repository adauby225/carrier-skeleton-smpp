package com.carrier.smpp.outbound.client;

public class MaxTpsDefault implements MaxRequestPerSecond{

	@Override
	public int calculateTpsByBind(BindTypes bindTypes, int tps) {
		if(tps >0)
			return (bindTypes.total()/tps) ;
		return 1;
	}

	

}
