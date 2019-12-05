package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_MESSAGE_MAP;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import com.carrier.smpp.model.esme.EsmeAccount;
import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;

public class CarrierSmppServerHandler implements SmppServerHandler{
	private final BindRequestHandler bindRequestHandler;
	private final EsmeAccountRepository esmeAccountRepository;
	private SessionManager sessionManager = SessionManager.getInstance();
	public CarrierSmppServerHandler(BindRequestHandler bindRequestHandler
			,EsmeAccountRepository esmeAccountRepository) {
		super();
		this.bindRequestHandler = bindRequestHandler;
		this.esmeAccountRepository = esmeAccountRepository;
	}

	@Override
	public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration,
			BaseBind bindRequest) throws SmppProcessingException {
		int response = bindRequestHandler.handleRequest(sessionConfiguration);
		if(response == STATUS_OK)
			return;
		throw new SmppProcessingException(response, STATUS_MESSAGE_MAP.get(response));
	}

	@Override
	public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp preparedBindResponse)
			throws SmppProcessingException {
		SmppSessionConfiguration config = session.getConfiguration();
		EsmeAccount account = esmeAccountRepository.findBySystemId(config.getSystemId());
		EsmeSmppSession esmeSession  =new EsmeSmppSession(session,account);
		sessionManager.addNewSession(sessionId,esmeSession);
		session.serverReady(new EsmeSmppSessionHandler(sessionId,esmeSession));
	}

	@Override
	public void sessionDestroyed(Long sessionId, SmppServerSession session) {
		
	}

}
