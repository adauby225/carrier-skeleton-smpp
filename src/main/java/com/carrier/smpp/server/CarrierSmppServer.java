package com.carrier.smpp.server;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carrier.smpp.CarrierSmppInstance;
import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppServer;
import com.cloudhopper.smpp.type.SmppChannelException;

public class CarrierSmppServer implements CarrierSmppInstance{
	private final Logger logger = LogManager.getLogger(CarrierSmppServer.class.getName());
	private ScheduledThreadPoolExecutor monitorExecutor;
	private ThreadPoolExecutor executor;
	private ConfigurationLoader<SmppServerConfiguration> configLoader;
	private DefaultSmppServer defaultSmppServer = null;
	private CarrierSmppServerHandler carrierSmppServerHandler;
	public CarrierSmppServer(ThreadPoolExecutor executor, ScheduledThreadPoolExecutor monitorExecutor
			, ConfigurationLoader<SmppServerConfiguration> configLoader, CarrierSmppServerHandler carrierSmppServerHandler) {
		this.executor = executor;
		this.monitorExecutor = monitorExecutor;
		this.configLoader = configLoader;
		this.carrierSmppServerHandler = carrierSmppServerHandler;
	}

	public CarrierSmppServer(ThreadPoolExecutor executor, ScheduledThreadPoolExecutor monitorExecutor,
			CarrierSmppServerHandler carrierSmppServerHandler) {
		this.executor = executor;
		this.monitorExecutor = monitorExecutor;
		this.carrierSmppServerHandler = carrierSmppServerHandler;
		this.configLoader = SmppServerConfiguration::new;
	}

	@Override
	public void start() {
		
		try {
			defaultSmppServer = new DefaultSmppServer(configLoader.loadConfig()
					, carrierSmppServerHandler ,executor, monitorExecutor);
			defaultSmppServer.start();
			SmppServerConfiguration config = defaultSmppServer.getConfiguration();
			logger.info("Server started at {} : {} \n Window size: {} " , config.getHost(), config.getPort()
					, config.getDefaultWindowSize());
		} catch (SmppChannelException e) {
			logger.error(e);
		}
	}

	@Override
	public void stop() {
		try {
			defaultSmppServer.destroy();
			monitorExecutor.shutdown();
			monitorExecutor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error(e);
		}catch(Exception e) {
			logger.error(e);
		}
	}

}
