package com.carrier.smpp.handler.pdu.response;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduResponse;

public class AsyncPduResponseHandler implements ResponseHandler<PduAsyncResponse> {
	private final Logger logger = LogManager.getLogger(AsyncPduResponseHandler.class.getName());
	private final ThreadPoolExecutor executor;
	private PduResponseHandlerFactory smscPduRespHandlerFactory;
	public AsyncPduResponseHandler(Map<Integer, ResponseHandler> responseHandlers,ThreadPoolExecutor executor) {
		this.smscPduRespHandlerFactory = new PduResponseHandlerFactory(responseHandlers);
		this.executor = executor;
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
