package com.carrier.smpp.outbound.client;

import static com.carrier.util.SkeletonExecutors.getExecutor;
import static com.carrier.util.SkeletonExecutors.getMonitorExecutor;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.esme.response.EsmeResponseHandler;
import com.carrier.smpp.model.esme.EsmeAccount;
import com.carrier.smpp.model.esme.EsmeAccountRepository;
import com.carrier.smpp.server.CarrierSmppServer;
import com.carrier.smpp.server.CarrierSmppServerHandler;
import com.carrier.smpp.server.ConfigParameter;
import com.carrier.smpp.server.ConfigurationLoader;
import com.carrier.smpp.server.DefaultEsmeBindRequestHandler;
import com.carrier.smpp.server.DefaultPasswordParameter;
import com.carrier.smpp.server.DefaultSystemIdParameter;
import com.carrier.smpp.server.EsmeSmppSession;
import com.carrier.smpp.server.HandlerException;
import com.carrier.smpp.server.SessionManager;
import com.carrier.smpp.service.BindExecutor;
import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.pdu.Unbind;

public class CarrierSmppConnectorTest {
	private final static Logger logger = LogManager.getLogger(CarrierSmppConnectorTest.class);
	public static final int PORT = 2785;
	public static final String SYSTEMID = "smppclient1";
	public static final String PASSWORD = "password";
	private static final String MESSAGE_ID = "fcc45-523kl-j8ep";

	/*private class EsmeAccountRepTest implements EsmeAccountRepository{
		final EsmeAccount account;
		private EsmeAccountRepTest(EsmeAccount account) {
			this.account = account;
		}
		@Override
		public EsmeAccount findBySystemId(String systemId) {
			return account;
		}
	}
	@Before
	public void resetSessionManager() throws NoSuchFieldException, SecurityException, IllegalArgumentException
	, IllegalAccessException {
		Field instance = SessionManager.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
	}


	@Test
	public void testConnectionToSmsc() throws InterruptedException, HandlerException {
		Map<Integer, EsmeRequestHandler>requestHandlers = new HashMap<>();
		Map<Integer, EsmeResponseHandler> responseHandlers = new HashMap<>();
		List<ConfigParameter>parameterCheckers = Arrays.asList(new DefaultSystemIdParameter(),new DefaultPasswordParameter());
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new DefaultEsmeBindRequestHandler(parameterCheckers,new EsmeAccountRepTest(new EsmeAccount(SYSTEMID, PASSWORD)))
				, new EsmeAccountRepTest(new EsmeAccount(SYSTEMID, PASSWORD)),requestHandlers,responseHandlers);

		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, new SmppSrvConfigLoader(), carrierSmppServerHandler);
		smppServer.start();
		ConnectorConfiguration connectorConfig = new ConnectorConfiguration("test.carrier.0",SYSTEMID, PASSWORD, "localhost", PORT);
		connectorConfig.setHost("127.0.0.1");

		BindTypes bindTypes = new BindTypes();
		CarrierSmppConnector connector = new CarrierSmppConnector(connectorConfig,bindTypes,BindExecutor::runBind);

		connector.connect();
		
		assertEquals(1, connector.getBinds().size());

		for(CarrierSmppBind bind: connector.getBinds())
			assertEquals(true,bind.isUp());

		connector.disconnect();
		SharedClientBootstrap sharedClientBootstrap = SharedClientBootstrap.getInstance();
		BindExecutor.stopAll();
		sharedClientBootstrap.stopClientBootStrap();
		smppServer.stop();


	}

	private class SmppSrvConfigLoader implements ConfigurationLoader<SmppServerConfiguration>{
		@Override
		public SmppServerConfiguration loadConfig() {
			SmppServerConfiguration configuration = new SmppServerConfiguration();
			configuration.setPort(PORT);
			configuration.setSystemId("carrier-skeleton");
			return configuration;
		}

	}

	private class SubmitSmHandler implements EsmeRequestHandler {

		@Override
		public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
			SubmitSm submitSm = (SubmitSm)pduRequest;
			SubmitSmResp submitSmResp = submitSm.createResponse();
			submitSmResp.setMessageId(MESSAGE_ID);
			return submitSmResp;
		}

	}

	private class UnbindHandler implements EsmeRequestHandler {

		@Override
		public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
			Unbind unbind = (Unbind)pduRequest;
			return unbind.createResponse();
		}

	}*/
	


}


