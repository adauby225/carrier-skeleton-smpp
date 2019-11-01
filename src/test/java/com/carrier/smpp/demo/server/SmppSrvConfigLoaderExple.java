package com.carrier.smpp.demo.server;

import com.carrier.smpp.server.ConfigurationLoader;
import com.cloudhopper.smpp.SmppServerConfiguration;

public class SmppSrvConfigLoaderExple implements ConfigurationLoader<SmppServerConfiguration> {

	@Override
	public SmppServerConfiguration loadConfig() {
		SmppServerConfiguration configuration = new SmppServerConfiguration();
		configuration.setPort(3027);
        configuration.setMaxConnectionSize(1);
        configuration.setNonBlockingSocketsEnabled(true);
        configuration.setDefaultRequestExpiryTimeout(30000);
        configuration.setDefaultWindowMonitorInterval(15000);
        configuration.setDefaultWindowSize(100);
        configuration.setDefaultWindowWaitTimeout(configuration.getDefaultRequestExpiryTimeout());
        configuration.setDefaultSessionCountersEnabled(true);
        configuration.setJmxEnabled(true);
		return configuration;
	}

}
