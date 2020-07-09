package com.carrier.smpp.model.esme;

import com.cloudhopper.smpp.SmppSessionConfiguration;

public interface EsmeAccountRepository {

	@Deprecated
	EsmeSmppAccount findBySystemId(String systemId);
	EsmeSmppAccount find(SmppSessionConfiguration sessionConfig);

}
