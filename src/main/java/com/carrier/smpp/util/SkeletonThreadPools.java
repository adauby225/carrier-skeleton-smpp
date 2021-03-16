package com.carrier.smpp.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class SkeletonThreadPools {
	private static final int CORE_POOL_SIZE = 1;
	private static final int ATOMIC_INITAL_VALUE=0;
	
	private SkeletonThreadPools() {
		throw new UnsupportedOperationException("Utility class");
	}

	public static ThreadPoolExecutor getNewCachedPool() {
		return (ThreadPoolExecutor)Executors.newCachedThreadPool();
	}

	public static ScheduledThreadPoolExecutor getMonitorExecutor() {
		return (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(CORE_POOL_SIZE, new ThreadFactory() {
			private AtomicInteger sequence = new AtomicInteger(ATOMIC_INITAL_VALUE);
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("SkeletonWindowMonitorPool-" + sequence.getAndIncrement());
				return thread;
			}
		});
	}
	

}
