package com.carrier.smpp.server;

import com.carrier.smpp.model.SmppAccount;
import com.cloudhopper.smpp.SmppSessionConfiguration;

public interface SmppAccountParamChecker {

	int check(SmppSessionConfiguration sessionConfiguration,SmppAccount smppAccount);

}
