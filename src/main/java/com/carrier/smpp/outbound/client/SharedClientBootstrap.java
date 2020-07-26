package com.carrier.smpp.outbound.client;

import static com.carrier.smpp.util.SkeletonExecutors.getExecutor;
import static com.carrier.smpp.util.SkeletonExecutors.getMonitorExecutor;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import com.cloudhopper.smpp.impl.DefaultSmppClient;

public class SharedClientBootstrap {
	private static final int CORE_POOL_SIZE=1;
	private static SharedClientBootstrap instance;
	private static ScheduledThreadPoolExecutor monitorExecutor = getMonitorExecutor();
	private static ThreadPoolExecutor executor = getExecutor();
	private DefaultSmppClient clientBootstrap =new DefaultSmppClient(executor, CORE_POOL_SIZE, monitorExecutor);
	
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
	public void stopClientBootStrap() {
		clientBootstrap.destroy();
		monitorExecutor.shutdownNow();
		executor.shutdownNow();
		
	}

}
