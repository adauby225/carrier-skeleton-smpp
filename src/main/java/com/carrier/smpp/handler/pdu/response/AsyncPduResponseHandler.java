package com.carrier.smpp.handler.pdu.response;

import static com.carrier.smpp.util.SkeletonThreadPools.getNewCachedPool;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduResponse;

public class AsyncPduResponseHandler implements ResponseHandler<PduAsyncResponse> {
	private final Logger logger = LogManager.getLogger(AsyncPduResponseHandler.class.getName());
	private final ThreadPoolExecutor executor = getNewCachedPool();
	private PduResponseHandlerFactory smscPduRespHandlerFactory;
	public AsyncPduResponseHandler(Map<Integer, ResponseHandler> responseHandlers) {
		this.smscPduRespHandlerFactory = new PduResponseHandlerFactory(responseHandlers);
	}

	@Override
	public void handleResponse(PduAsyncResponse asyncResponse) {
		try {
			PduResponse resp = asyncResponse.getResponse();
			ResponseHandler<PduAsyncResponse>respHandler = smscPduRespHandlerFactory
					.getHandler(resp.getCommandId());
			executor.execute(new HandlingResponseTask(respHandler,asyncResponse));
		}catch(Exception e) {
			logger.error(e);
		}
		

	}

}
