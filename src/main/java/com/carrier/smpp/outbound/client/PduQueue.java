package com.carrier.smpp.outbound.client;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.cloudhopper.smpp.pdu.PduRequest;

public class PduQueue {
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock writeLock = lock.writeLock();
	private final Lock readLock =  lock.readLock();
	private Deque<PduRequest> fireRequestReceived = new LinkedList<>(); 
	
	public boolean isEmpty() {
		return false;
	}

	public PduRequest takeFirstRequest() {
		writeLock.lock();
		PduRequest request = fireRequestReceived.pollFirst();
		writeLock.unlock();
		return request;
	}
	
	public void addRequestLast(PduRequest pdu) {
		writeLock.lock();
		this.fireRequestReceived.addLast(pdu);
		writeLock.unlock();
	}
	
	public void addRequestFirst(PduRequest pdu) {
		writeLock.lock();
		this.fireRequestReceived.addFirst(pdu);
		writeLock.unlock();
	}
	
	public int size() {
		readLock.lock();
		int size = fireRequestReceived.size();
		readLock.unlock();
		return size;
	}
	
}
