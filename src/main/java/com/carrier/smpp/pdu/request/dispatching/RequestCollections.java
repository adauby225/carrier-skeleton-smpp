package com.carrier.smpp.pdu.request.dispatching;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.cloudhopper.commons.util.LoadBalancedList;
import com.cloudhopper.commons.util.LoadBalancedList.Node;
import com.cloudhopper.commons.util.LoadBalancedLists;
import com.cloudhopper.commons.util.RoundRobinLoadBalancedList;
import com.cloudhopper.smpp.pdu.PduRequest;

public class RequestCollections implements  RequestManager{
	protected static final int MAX_CONTENT=1000;
	private static final int EMPTY = 0;
	protected AtomicInteger collected = new AtomicInteger(0);
	protected AtomicBoolean isOpen = new AtomicBoolean(true);

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	protected final Lock writeLock = lock.writeLock();
	protected final Lock readLock =  lock.readLock();
	private final LoadBalancedList<RequestCollection> balancedList = LoadBalancedLists.synchronizedList(new RoundRobinLoadBalancedList<RequestCollection>());

	public RequestCollections() {
		balancedList.set(new RequestQueue(), 1);
		balancedList.set(new RequestStack(), 1);
	}

	@Override
	public void addRequest(PduRequest request) {
		writeLock.lock();
		RequestCollection collection = balancedList.getNext();
		collection.addRequest(request);
		collected.getAndIncrement();
		writeLock.unlock();
	}

	@Override
	public Optional<PduRequest> nextRequest() {
		writeLock.lock();
		RequestCollection collection = balancedList.getNext();
		PduRequest request = collection.nextRequest();
		writeLock.unlock();
		if(request==null)
			return Optional.empty();
		collected.getAndDecrement();

		return Optional.of(request);
	}

	@Override
	public long sizeOfRequests() {
		readLock.lock();
		long requests=0;
		List<Node<RequestCollection>>nodes = balancedList.getValues();
		for( Node<RequestCollection>node: nodes) {
			RequestCollection collection = node.getValue();
			requests  = requests + collection.size();
		}
		readLock.unlock();
		return requests;
	}

	@Override
	public boolean isCollectionOpen() {
		return isOpen.get();
	}

	@Override
	public void openCollection() {
		if(!isOpen.get() && collected.get() == EMPTY ) 
			isOpen.set(true);
	}

	@Override
	public void closeCollection() {
		if(isOpen.get() && collected.get() >= MAX_CONTENT)
			isOpen.set(false);

	}


}
