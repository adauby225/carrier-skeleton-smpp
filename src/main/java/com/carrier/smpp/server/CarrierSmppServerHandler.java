package com.carrier.smpp.server;

import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;

public class CarrierSmppServerHandler implements SmppServerHandler{
	

	@Override
	public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration,
			BaseBind bindRequest) throws SmppProcessingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp preparedBindResponse)
			throws SmppProcessingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(Long sessionId, SmppServerSession session) {
		// TODO Auto-generated method stub
		
	}

}
