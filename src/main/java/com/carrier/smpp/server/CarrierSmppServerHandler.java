package com.carrier.smpp.server;

import static com.cloudhopper.smpp.SmppConstants.STATUS_MESSAGE_MAP;
import static com.cloudhopper.smpp.SmppConstants.STATUS_OK;

import com.carrier.smpp.pdu.Handlers;
import com.carrier.smpp.handler.pdu.request.RequestHandler;
import com.carrier.smpp.model.SmppAccount;
import com.carrier.smpp.model.SmppAccountRepository;
import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;

public class CarrierSmppServerHandler implements SmppServerHandler{
	private final RequestHandler bindRequestHandler;
	private final SmppAccountRepository accountRepository;
	private SessionManager sessionManager = SessionManager.getInstance();
	private Handlers handlers;
	public CarrierSmppServerHandler(RequestHandler bindRequestHandler
			,SmppAccountRepository accountRepository, Handlers handlers) {
		super();
		this.bindRequestHandler = bindRequestHandler;
		this.accountRepository = accountRepository;
		this.handlers  = handlers;
	}

	@Override
	public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration,
			BaseBind bindRequest) throws SmppProcessingException {
		int response = (int) bindRequestHandler.handleRequest(sessionConfiguration);
		if(response == STATUS_OK)
			return;
		throw new SmppProcessingException(response, STATUS_MESSAGE_MAP.get(response));
	}

	@Override
	public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp preparedBindResponse)
			throws SmppProcessingException {
		SmppSessionConfiguration config = session.getConfiguration();
		SmppAccount account = accountRepository.find(config);
		EsmeSmppSession newEsmeSession  = new EsmeSmppSession(sessionId, session,account);
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
