package com.carrier.smpp.outbound.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.server.EsmeSmppSession;
import com.carrier.smpp.service.BindExecutor;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.Unbind;
import com.cloudhopper.smpp.simulator.SmppSimulatorBindProcessor;
import com.cloudhopper.smpp.simulator.SmppSimulatorServer;
import com.cloudhopper.smpp.util.DaemonExecutors;

public class CarrierSmppConnectorTest {
	private final static Logger logger = LogManager.getLogger(CarrierSmppConnectorTest.class);
	public static final int PORT = 2785;
	public static final String SYSTEMID = "smppclient1";
	public static final String PASSWORD = "password";

	static SmppSimulatorServer server;
	

	@BeforeClass
	public static void startSimulator() {
		server = new SmppSimulatorServer(DaemonExecutors.newCachedDaemonThreadPool());
		server.start(PORT);

	}

	@AfterClass
	public static void stopSimulator() {
		logger.info("Stopping the server inside test class");
		server.stop();
		logger.info("Stopping the boostrap inside test class");
		
	}

	public void clearAllServerSessions() {
		server.getHandler().getSessionQueue().clear();
	}

	public void registerServerBindProcessor() {
		server.getHandler().setDefaultPduProcessor(new SmppSimulatorBindProcessor(SYSTEMID, PASSWORD));
	}

	public void unregisterServerBindProcessor() {
		server.getHandler().setDefaultPduProcessor(null);
	}




	@Test
	public void testConnectionToSmsc() throws InterruptedException {
		
		registerServerBindProcessor();
		SmppOutboundSettings settings = new SmppOutboundSettings(SYSTEMID, PASSWORD);

		settings.setName("test.carrier.0");
		settings.setHost("127.0.0.1");
		settings.setPort(PORT);
		settings.setConnectTimeout(100);
		settings.setBindTimeout(100);
		settings.getLoggingOptions().setLogBytes(false);
		// to enable monitoring (request expiration)
		settings.setCountersEnabled(true);
		BindTypes bindTypes = new BindTypes();
		CarrierSmppConnector connector = new CarrierSmppConnector(settings,bindTypes,BindExecutor::runBind);

		connector.connect();
		assertEquals(1, connector.getBinds().size());

		for(CarrierSmppBind bind: connector.getBinds())
			assertEquals(true,bind.isUp());

		connector.disconnect();


	}

	/*private class SmppSrvConfigLoader implements ConfigurationLoader<SmppServerConfiguration>{
		@Override
		public SmppServerConfiguration loadConfig() {
			SmppServerConfiguration configuration = new SmppServerConfiguration();
			configuration.setPort(PORT);
			configuration.setSystemId("carrier-skeleton");
			return configuration;
		}

	}

	/*private class SubmitSmHandler implements EsmeRequestHandler {

		@Override
		public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
			SubmitSm submitSm = (SubmitSm)pduRequest;
			SubmitSmResp submitSmResp = submitSm.createResponse();
			submitSmResp.setMessageId(MESSAGE_ID);
			return submitSmResp;
		}

	}*/

	/*private class UnbindHandler implements EsmeRequestHandler {

		@Override
		public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
			Unbind unbind = (Unbind)pduRequest;
			return unbind.createResponse();
		}

	}*/



}


