package com.carrier.smpp.server;

import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public interface SmppAccountParamCheckable {

	int check(SmppSessionConfiguration sessionConfiguration,EsmeSmppAccount smppAccount);

}
