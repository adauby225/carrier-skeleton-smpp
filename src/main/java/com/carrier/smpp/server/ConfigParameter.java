package com.carrier.smpp.server;

import com.carrier.smpp.model.esme.EsmeSessionConfigParams;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public interface ConfigParameter {

	int check(SmppSessionConfiguration sessionConfiguration,EsmeSessionConfigParams configParams);

}
