package com.carrier.smpp.outbound.client;

public class DefaultMaxTpsCalculator implements MaxRequestPerSecond{

	@Override
	public int calculateTpsByBind(BindTypes bindTypes, int tps) {
		int totalBinds = bindTypes.totalTrxAndTx();
		if(tps >0 && totalBinds<tps)
			return (tps/totalBinds) ;
		return 1;
	}

	

}
