package com.carrier.smpp.outbound.client;

public interface MaxRequestPerSecond {
	/**
	 * Calculate throughput per bind 
	 * @param bindTypes : <code> BindTypes </code>
	 * @param tps : <code> int </code>
	 * @return <code> int </code> corresponding to throughput for each created bind
	 */
	int calculateTpsByBind(BindTypes bindTypes,int tps);

}
