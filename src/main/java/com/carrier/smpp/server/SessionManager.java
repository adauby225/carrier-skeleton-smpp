package com.carrier.smpp.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class SessionManager {
	private static SessionManager instance;
	private ConcurrentMap<Long,EsmeSmppSession> sessions= new ConcurrentHashMap<>();
	private SessionManager() {}

	public static synchronized SessionManager getInstance() {
		if(instance==null) 
			instance = new SessionManager();
		return instance;
	}

	public void addNewSession(Long sessionId, EsmeSmppSession esmeSession) {
		sessions.put(sessionId,esmeSession);
	}

	public  int sessionsSize() {
		return sessions.size();
	}

	public EsmeSmppSession removeSession(Long sessionId) {
		return sessions.remove(sessionId);
	}

	public ConcurrentMap<Long,EsmeSmppSession> getSessions() {
		return sessions;
	}
}
