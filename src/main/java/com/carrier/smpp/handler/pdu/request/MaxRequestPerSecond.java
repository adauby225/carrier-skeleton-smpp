package com.carrier.smpp.handler.pdu.request;

import com.carrier.smpp.outbound.client.BindTypes;

public interface MaxRequestPerSecond {
	/**
	 * Calculate throughput per bind 
	 * @param bindTypes : <code> BindTypes </code>
	 * @param tps : <code> int </code>
	 * @return <code> int </code> corresponding to throughput for each created bind
	 */
	int calculateTpsByBind(BindTypes bindTypes,int tps);

}
