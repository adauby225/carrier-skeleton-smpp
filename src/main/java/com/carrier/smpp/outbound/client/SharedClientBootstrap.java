package com.carrier.smpp.outbound.client;

import static com.carrier.util.SkeletonExecutors.getExecutor;
import static com.carrier.util.SkeletonExecutors.getMonitorExecutor;

import com.cloudhopper.smpp.impl.DefaultSmppClient;

public class SharedClientBootstrap {
	private static final int CORE_POOL_SIZE=1;
	private static SharedClientBootstrap instance;
	
	private DefaultSmppClient clientBootstrap =new DefaultSmppClient(getExecutor(), CORE_POOL_SIZE, getMonitorExecutor());
	
	private SharedClientBootstrap() {
		
	}
	public static synchronized  SharedClientBootstrap getInstance(){
		if(instance==null)
			instance = new SharedClientBootstrap();
		return instance;
	}
	
	public DefaultSmppClient getClientBootstrap() {
		return clientBootstrap;
	}

}
