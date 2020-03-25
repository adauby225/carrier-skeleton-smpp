package com.carrier.smpp.server;

import static com.carrier.util.SkeletonExecutors.getExecutor;
import static com.carrier.util.SkeletonExecutors.getMonitorExecutor;
import static com.cloudhopper.smpp.SmppBindType.TRANSCEIVER;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVPASWD;
import static com.cloudhopper.smpp.SmppConstants.STATUS_INVSYSID;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.carrier.smpp.esme.request.EsmeRequestHandler;
import com.carrier.smpp.esme.response.EsmeResponseHandler;
import com.carrier.smpp.model.esme.EsmeAccountRepository;
import com.carrier.smpp.model.esme.EsmeSmppAccount;
import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.pdu.Unbind;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppBindException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;

public class CarrierServerSmppTest {
	private static final int PORT = 5800;
	private static final String SYSTEMID = "client1";
	private static final String PASSWD = "passwd01";
	private static final String MESSAGE_ID = "fcc45-523kl-j8ep";
	private PduhandlersTester pduHandlers = new PduhandlersTester();
	private class EsmeAccountRepTest implements EsmeAccountRepository{
		final EsmeSmppAccount account;
		private EsmeAccountRepTest(EsmeSmppAccount account) {
			this.account = account;
		}
		@Override
		public EsmeSmppAccount findBySystemId(String systemId) {
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
	public void testSessionOk() throws Exception{
		
		List<ConfigParameter>parameterCheckers = Arrays.asList(new DefaultSystemIdParameter(),new DefaultPasswordParameter());
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new DefaultEsmeBindRequestHandler(parameterCheckers,new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, PASSWD)))
				, new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, PASSWD)),pduHandlers);

		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, new SmppSrvConfigLoader(), carrierSmppServerHandler);
		smppServer.start();
		try {
			DefaultSmppClient client0 = new DefaultSmppClient();
			SmppSessionConfiguration sessionConfig0 = createClientConfiguration();
			client0.bind(sessionConfig0);
			SessionManager sessionManager = SessionManager.getInstance();
			Map<Long, EsmeSmppSession> smppSessions = sessionManager.getSessions();
			EsmeSmppSession esmeSession = smppSessions.values().iterator().next();
			SmppSession smppSession = esmeSession.getSmppSession();
			assertEquals(1, sessionManager.sessionsSize());
			assertEquals(SYSTEMID,smppSession.getConfiguration().getSystemId());
			assertEquals(PASSWD, smppSession.getConfiguration().getPassword());
			assertEquals(true, smppSession.isBound());
			smppSession.close();
		}finally {
			smppServer.stop();
		}


	}

	@Test
	public void testInvalidSystemId() throws SmppBindException, SmppTimeoutException, SmppChannelException, UnrecoverablePduException, InterruptedException
	, HandlerException {
		List<ConfigParameter>parameterCheckers = Arrays.asList(new DefaultSystemIdParameter(),new DefaultPasswordParameter());
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new DefaultEsmeBindRequestHandler(parameterCheckers,new EsmeAccountRepTest(new EsmeSmppAccount("WRONG_SYS_ID", PASSWD)))
				, new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, PASSWD)),pduHandlers);

		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, new SmppSrvConfigLoader(), carrierSmppServerHandler);
		smppServer.start();
		try {
			DefaultSmppClient client0 = new DefaultSmppClient();
			SmppSessionConfiguration sessionConfig0 = createClientConfiguration();
			client0.bind(sessionConfig0);
		}catch(SmppBindException e) {
			assertEquals(STATUS_INVSYSID, e.getBindResponse().getCommandStatus());
		}finally {
			smppServer.stop();
		}
		

	}
	
	@Test
	public void testInvalidPassword() throws SmppBindException, SmppTimeoutException, SmppChannelException, UnrecoverablePduException, InterruptedException
	, HandlerException {
		
		List<ConfigParameter>parameterCheckers = Arrays.asList(new DefaultSystemIdParameter(),new DefaultPasswordParameter());
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new DefaultEsmeBindRequestHandler(parameterCheckers,new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, "bad_passwd")))
				, new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, PASSWD)),pduHandlers);

		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, new SmppSrvConfigLoader(), carrierSmppServerHandler);
		DefaultSmppClient client0 = new DefaultSmppClient();
		smppServer.start();
		try {
			
			SmppSessionConfiguration sessionConfig0 = createClientConfiguration();
			client0.bind(sessionConfig0);
		}catch(SmppBindException e) {
			assertEquals(STATUS_INVPASWD, e.getBindResponse().getCommandStatus());
		}finally {
			client0.destroy();
			smppServer.stop();
		}
	}
	
	@Test
	public void testHandleSubmitSm() throws SmppBindException, SmppTimeoutException, SmppChannelException, UnrecoverablePduException
	, InterruptedException, SmppInvalidArgumentException, RecoverablePduException, HandlerException {
		
		List<ConfigParameter>parameterCheckers = Arrays.asList(new DefaultSystemIdParameter(),new DefaultPasswordParameter());
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new DefaultEsmeBindRequestHandler(parameterCheckers,new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, PASSWD)))
				, new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, PASSWD)),pduHandlers);

		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, new SmppSrvConfigLoader(), carrierSmppServerHandler);
		DefaultSmppClient client0 = new DefaultSmppClient();
		try {
			smppServer.start();
			
			SmppSessionConfiguration sessionConfig0 = createClientConfiguration();
			SmppSession smppSession = client0.bind(sessionConfig0);
			SubmitSmResp submitResp = smppSession.submit(newSubmitSm(), 1000);
			assertEquals(SmppConstants.STATUS_OK, submitResp.getCommandStatus());
			assertEquals(MESSAGE_ID, submitResp.getMessageId());
		}finally {
			client0.destroy();
			smppServer.stop();
		}

	}
	
	@Test
	public void testHandleUnbind() throws SmppBindException, SmppTimeoutException, SmppChannelException, UnrecoverablePduException, InterruptedException
	, SmppInvalidArgumentException, RecoverablePduException, HandlerException {
		Map<Integer, EsmeResponseHandler> responseHandlers = new HashMap<>();
		Map<Integer, EsmeRequestHandler>requestHandlers = new HashMap<>();
		//handlers.put(SmppConstants.CMD_ID_SUBMIT_SM, new SubmitSmHandler());
		requestHandlers.put(SmppConstants.CMD_ID_UNBIND, new UnbindHandler());
		
		List<ConfigParameter>parameterCheckers = Arrays.asList(new DefaultSystemIdParameter(),new DefaultPasswordParameter());
		CarrierSmppServerHandler carrierSmppServerHandler = new CarrierSmppServerHandler(
				new DefaultEsmeBindRequestHandler(parameterCheckers,new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, PASSWD)))
				, new EsmeAccountRepTest(new EsmeSmppAccount(SYSTEMID, PASSWD)),pduHandlers);

		CarrierSmppServer smppServer = new CarrierSmppServer(getExecutor(),getMonitorExecutor()
				, new SmppSrvConfigLoader(), carrierSmppServerHandler);
		DefaultSmppClient client0 = new DefaultSmppClient();
		try {
			SessionManager sessionManger = SessionManager.getInstance();
			smppServer.start();
			SmppSessionConfiguration sessionConfig0 = createClientConfiguration();
			SmppSession smppSession = client0.bind(sessionConfig0);
			assertEquals(1, sessionManger.sessionsSize());
			smppSession.unbind(1000);
			//Thread.sleep(500);
			assertEquals(0, sessionManger.sessionsSize());
			assertEquals(true, smppSession.isClosed());
		}finally {
			client0.destroy();
			smppServer.stop();
		}
	}
	private SubmitSm newSubmitSm() throws SmppInvalidArgumentException {
		String text160 = "\u20AC Lorem [ipsum] dolor sit amet, consectetur adipiscing elit. Proin feugiat, leo id commodo tincidunt, nibh diam ornare est, vitae accumsan risus lacus sed sem metus.";
        byte[] textBytes = CharsetUtil.encode(text160, CharsetUtil.CHARSET_GSM);
        
        SubmitSm submit0 = new SubmitSm();

        submit0.setSourceAddress(new Address((byte)0x03, (byte)0x00, "40404"));
        submit0.setDestAddress(new Address((byte)0x01, (byte)0x01, "22544301205"));
        submit0.setShortMessage(textBytes);
		return submit0;
	}

	private SmppSessionConfiguration createClientConfiguration() {
		SmppSessionConfiguration configuration = new SmppSessionConfiguration();
		configuration.setWindowSize(1);
		configuration.setName("Tester.Session.0");
		configuration.setType(TRANSCEIVER);
		configuration.setHost("localhost");
		configuration.setPort(PORT);
		configuration.setConnectTimeout(1000);
		configuration.setBindTimeout(1000);
		configuration.setSystemId(SYSTEMID);
		configuration.setPassword(PASSWD);
		configuration.getLoggingOptions().setLogBytes(true);
		return configuration;
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
	
	 class SubmitSmHandler implements EsmeRequestHandler {

		@Override
		public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
			SubmitSm submitSm = (SubmitSm)pduRequest;
			SubmitSmResp submitSmResp = submitSm.createResponse();
			submitSmResp.setMessageId(MESSAGE_ID);
			return submitSmResp;
		}

	}
	
	 class UnbindHandler implements EsmeRequestHandler {

		@Override
		public PduResponse handleRequest(PduRequest pduRequest, EsmeSmppSession esmeSmppSession) {
			Unbind unbind = (Unbind)pduRequest;
			return unbind.createResponse();
		}

	}




}
