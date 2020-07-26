package com.carrier.smpp.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BindExecutor {
	private static final Logger logger = LogManager.getLogger(BindExecutor.class.getName());
	private static ExecutorService bindPool = Executors.newCachedThreadPool();
	private BindExecutor() {}

	public static void runBind(Runnable bind) {
		bindPool.execute(bind);
	}

	public static void stopAll() throws InterruptedException {
		try {
			bindPool.shutdown();
			bindPool.awaitTermination(30, TimeUnit.SECONDS);
		}catch(InterruptedException e) {
			logger.error(e);
			Thread currentThread = Thread.currentThread();
			currentThread.interrupt();
		}finally {
			if (!bindPool.isTerminated()) 
				logger.error("cancelling all non-finished tasks");
			
			bindPool.shutdownNow();
			logger.info("shutdown finished.");

		}

	}


}
