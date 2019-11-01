package com.carrier.smpp.server;

public interface ConfigurationLoader<T> {
	T loadConfig();
}
