package com.carrier.smpp.model.esme;

import com.cloudhopper.smpp.SmppSessionConfiguration;

public interface EsmeAccountRepository {

	EsmeSmppAccount find(SmppSessionConfiguration sessionConfig);

}
