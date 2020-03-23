package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_MESSAGE_MAP;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import com.carrier.smpp.esme.request.Handlers;
import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.carrier.smpp.model.esme.EsmeAccountRepository;
import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;

public class CarrierSmppServerHandler implements SmppServerHandler{
	private final BindRequestHandler bindRequestHandler;
	private final EsmeAccountRepository accountRepository;
	private SessionManager sessionManager = SessionManager.getInstance();
	private Handlers handlers;
	public CarrierSmppServerHandler(BindRequestHandler bindRequestHandler
			,EsmeAccountRepository accountRepository, Handlers handlers) {
		super();
		this.bindRequestHandler = bindRequestHandler;
		this.accountRepository = accountRepository;
		this.handlers  = handlers;
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
		EsmeSmppAccount account = accountRepository.findBySystemId(config.getSystemId());
		EsmeSmppSession newEsmeSession  = new EsmeSmppSession(session,account);
		session.serverReady(new EsmeSmppSessionHandler(sessionId,newEsmeSession,handlers.getrequestHandlers()
				, handlers.getResponseHandlers()));
		sessionManager.addNewSession(sessionId,newEsmeSession);
	}

	@Override
	public void sessionDestroyed(Long sessionId, SmppServerSession session) {
		session.destroy();
		sessionManager.removeSession(sessionId);
	}
}
