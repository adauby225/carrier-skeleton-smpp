package com.carrier.smpp.outbound.client;

public interface MaxRequestPerSecond {

	int calculateTpsByBind(BindTypes bindTypes,int tps);

}
