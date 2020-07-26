package com.carrier.smpp.esme.request;

import static com.carrier.smpp.util.Values.ONE_SECOND_IN_MILLIS;

import org.apache.commons.lang3.time.StopWatch;

public class RequestCounter {
	private static final int FIRST_REQUEST=1;
	private static final int INITIAL_VALUE = 0;
	private StopWatch stopWatch = new StopWatch();
	private int counter=INITIAL_VALUE;

	public boolean isRequestAuthorized(int tps) {
		boolean isAccepted=true;
		counter= counter + 1;
		startStopWatchIfFirstRequest();
		if(isTpsOverFlow(tps))
			isAccepted = false;
		resetAll();
		return isAccepted;
	}

	private void resetAll() {
		if(stopWatch.getTime()>=ONE_SECOND_IN_MILLIS) {
			counter=INITIAL_VALUE;
			stopWatch.stop();
			stopWatch.reset();
		}
	}

	private boolean isTpsOverFlow(int tps) {
		return counter>tps && stopWatch.getTime()<=ONE_SECOND_IN_MILLIS;
	}

	private void startStopWatchIfFirstRequest() {
		if(counter == FIRST_REQUEST) {
			stopWatch.start();
		}
	}




}
