package com.carrier.smpp.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.carrier.smpp.model.esme.EsmeSmppAccount;

public final class SessionManager {
	private static SessionManager instance;
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();
	
	private Map<Long,EsmeSmppSession> sessions= new HashMap<>();
	private SessionManager() {}

	public static synchronized SessionManager getInstance() {
		if(instance==null) 
			instance = new SessionManager();
		return instance;
	}

	public void addNewSession(Long sessionId, EsmeSmppSession esmeSession) {
		writeLock.lock();
		sessions.put(sessionId,esmeSession);
		writeLock.unlock();
	}

	public  int sessionsSize() {
		readLock.lock();
		int sessionsSize = sessions.size();
		readLock.unlock();
		return sessionsSize;
	}

	public EsmeSmppSession removeSession(Long sessionId) {
		writeLock.lock();
		EsmeSmppSession session = sessions.remove(sessionId);
		writeLock.unlock();
		session.destroySession();
		return session;
	}
	
	public Optional<EsmeSmppSession> findSessionBySystemId(String systemId) {
		readLock.lock();
		for(EsmeSmppSession session : sessions.values()) {
			EsmeSmppAccount smppAccount = session.getAccount();
			if(systemId.equals(smppAccount.getSystemId())) {
				readLock.unlock();
				return Optional.of(session);
			}
		}
		readLock.unlock();
		return Optional.empty();
	}

	public Map<Long,EsmeSmppSession> getSessions() {
		Map<Long, EsmeSmppSession>smppSessions = new HashMap<>();
		readLock.lock();
		
		for(Entry<Long, EsmeSmppSession>entry : sessions.entrySet()) 
			smppSessions.put(entry.getKey(), entry.getValue());
		
		readLock.unlock();
		return smppSessions;
	}
	
}
