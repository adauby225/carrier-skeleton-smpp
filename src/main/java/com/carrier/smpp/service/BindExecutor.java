package com.carrier.smpp.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BindExecutor {
	private static ExecutorService bindPool = Executors.newCachedThreadPool();
	private BindExecutor() {}
	
	public static void runBind(Runnable bind) {
		bindPool.execute(bind);
	}
	

}
