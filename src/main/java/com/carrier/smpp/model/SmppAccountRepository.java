package com.carrier.smpp.model;

import com.cloudhopper.smpp.SmppSessionConfiguration;

public interface SmppAccountRepository<T> {

	SmppAccount find(T t);

}
