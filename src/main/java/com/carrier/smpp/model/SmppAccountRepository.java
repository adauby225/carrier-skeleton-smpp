package com.carrier.smpp.model;

public interface SmppAccountRepository<T> {

	SmppAccount find(T t);

}
