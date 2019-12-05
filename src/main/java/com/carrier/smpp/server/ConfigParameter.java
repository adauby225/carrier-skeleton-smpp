package com.carrier.smpp.server;

import com.carrier.smpp.model.esme.EsmeAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public interface ConfigParameter {

	int check(SmppSessionConfiguration sessionConfiguration,EsmeAccount configParams);

}
