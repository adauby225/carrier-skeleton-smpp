package com.carrier.smpp.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BindExecutor {
	private static ExecutorService bindPool = Executors.newCachedThreadPool();
	private BindExecutor() {}
	
	public static void runBind(Runnable bind) {
		bindPool.execute(bind);
	}
	
	public static void stopAll() throws InterruptedException {
		bindPool.shutdown();
		bindPool.awaitTermination(5, TimeUnit.MINUTES);
	}
	

}
