package com.carrier.smpp.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadUtil {
	private static final Logger logger = LogManager.getLogger(ThreadUtil.class);
	private ThreadUtil() {}
	
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			logger.error(e);
			Thread currentThread = Thread.currentThread();
			currentThread.interrupt();
		}
	}
}
