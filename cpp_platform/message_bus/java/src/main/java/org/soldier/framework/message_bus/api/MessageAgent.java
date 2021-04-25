package org.soldier.framework.message_bus.api;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer.ConsumeResult;
import org.soldier.framework.message_bus.api.IMessageConsumer.StartUpMode;
import org.soldier.framework.message_bus.swig.GuardPolicySwig;
import org.soldier.framework.message_bus.swig.IMessageConsumerSwig;
import org.soldier.framework.message_bus.swig.MessageAgentBridgeConstants;
import org.soldier.framework.message_bus.swig.MessageAgentSwig;
import org.soldier.platform.svr_platform.util.ProtocolUtil;

public class MessageAgent extends IMessageConsumerSwig {
	private static final String INIT_TOPIC = "#init#";
	private static final TProtocolFactory FACTORY = new TCompactProtocol.Factory();
	private static final int MAX_MESSAGE_PACKET_LENGTH = 4000;
	
	public static void initNativeLogName(String name) {
		MessageAgentSwig.initNativeLogName(name);
	}
	
	static {
		LibraryLoader.init();
	}
	
	private String agentName;
	private IMessageConsumer consumer;
	
	private static class ConsumeEntry {
		public Method consumerMethod;
		public Class<? extends TBase> messageType;
	}
	private Map<String, ConsumeEntry> consumerCallbackMethodMap = new HashMap<String, ConsumeEntry>();
	
	public MessageAgent(IMessageConsumer consumer
			, String agentName
			, File graphFile) {
		if (consumer == null) {
			throw new IllegalArgumentException("consumer should not be null");
		}
		if (StringUtils.isEmpty(agentName)) {
			throw new IllegalArgumentException("agentName should not be null or empty");
		}
		if (graphFile == null) {
			throw new IllegalArgumentException("graphFile should not be null or empty");
		}
		if (!graphFile.exists()) {
			throw new IllegalStateException(graphFile.getAbsolutePath() + " is not existed, message broker is not started!");
		}
		
		this.agentName = agentName;
		this.consumer = consumer;
		construct();
		
		if (!MessageAgentSwig.initMessageAgent(agentName, graphFile.getAbsolutePath(), this)) {
			throw new IllegalStateException("init message agent " + agentName + " failed!");
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				MessageAgent.this.destory();
			}
		});
	}
	
	public void destory() {
		MessageAgentSwig.destroyMessageAgent(agentName);
	}
	
	private void construct() {
		for (Method consumerMethod : consumer.getClass().getDeclaredMethods()) {
			AppLog.d("scan consumer " + consumer.getClass().getName() + " method=" + consumerMethod.getName());
			
			consume[] consumeTags = consumerMethod.getAnnotationsByType(consume.class);
			if (consumeTags == null || consumeTags.length == 0) {
				continue;
			}
			if (consumeTags.length != 1) {
				throw new IllegalArgumentException(consumer.getClass().getName() + "'s method(" 
						+ consumerMethod.getName()
						+ ") has multi consume annotation");
			}
			consume uniqueConsumeTag = consumeTags[0];
			
			if (consumerMethod.getParameterCount() != 1) {
				throw new IllegalArgumentException(consumer.getClass().getName() + "'s method(" 
						+ consumerMethod.getName()
						+ ") should noly have one parameter");
			}
			
			if (consumerMethod.getReturnType() != ConsumeResult.class) {
				throw new IllegalArgumentException(consumer.getClass().getName() + "'s method(" 
						+ consumerMethod.getName()
						+ ") should return " + ConsumeResult.class.getSimpleName());
			}
			
			if (uniqueConsumeTag.value() != consumerMethod.getParameterTypes()[0]) {
				throw new IllegalArgumentException(consumer.getClass().getName() + "'s method(" 
						+ consumerMethod.getName()
						+ ") parameter class is not tag type");
			}
			
			if (consumerCallbackMethodMap.containsKey(uniqueConsumeTag.value().getSimpleName())) {
				throw new IllegalArgumentException(
						consumer.getClass().getName() + "'s method(" 
								+ consumerMethod.getName()
								+ ") has handle multi annotation for " 
								+ uniqueConsumeTag.value().getSimpleName() + " method");
			}
			
			consumerMethod.setAccessible(true);
			
			ConsumeEntry entry = new ConsumeEntry();
			entry.consumerMethod = consumerMethod;
			entry.messageType = uniqueConsumeTag.value();
			
			consumerCallbackMethodMap.put(uniqueConsumeTag.value().getSimpleName(), entry);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String prepareGuardMessage(TBase message) {
		return prepareGuardMessage(message, new TimeoutGuardPolicy());
	}
	
	@SuppressWarnings("rawtypes")
	public String prepareGuardMessage(TBase message, IGuardPolicy policy) {
		if (policy == null) {
			throw new IllegalArgumentException("policy should not be null");
		}
		
		byte[] messageData = createMessageData(message);
		
		GuardPolicySwig swigPolicy = new GuardPolicySwig();
		if (policy instanceof TimeoutGuardPolicy) {
			swigPolicy.setType(MessageAgentBridgeConstants.GUARD_POLICY_TIMEOUT);
			swigPolicy.setTimeout_seconds(((TimeoutGuardPolicy)policy).getTimeoutSeconds());
		} else {
			throw new IllegalArgumentException("unsupported guard policy type");
		}
		
		String guardId = MessageAgentSwig.prepareGuardMessage(agentName
				, message.getClass().getSimpleName()
				, messageData
				, swigPolicy);
		if (AppLog.debugEnabled()) {
			AppLog.d("prepareGuardMessage policy=" + policy + ", guardId=" + guardId
					+ ", message=" + message);
		}
		return guardId;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean sendMessageAndRmGuard(TBase message, String guardId) {
		byte[] messageData = createMessageData(message);
		if (guardId == null) {
			throw new IllegalArgumentException("guardId should not be null");
		}
		
		if (AppLog.debugEnabled()) {
			AppLog.d("sendMessageAndRmGuard guardId=" + guardId + ", message=" + message);
		}
		
		return MessageAgentSwig.sendMessageAndRmGuard(agentName
				, message.getClass().getSimpleName()
				, messageData
				, guardId);
	}
	
	public boolean rmGuardMessage(String guardId) {
		if (guardId == null) {
			throw new IllegalArgumentException("guardId should not be null");
		}
		
		if (AppLog.debugEnabled()) {
			AppLog.d("rmGuardMessage guardId=" + guardId);
		}
		return MessageAgentSwig.rmGuardMessage(agentName, guardId);
	}
	
	@SuppressWarnings("rawtypes")
	public boolean sendMessageDirectly(TBase message) {
		byte[] messageData = createMessageData(message);
		
		if (AppLog.debugEnabled()) {
		    AppLog.d("sendMessageDirectly " + message);
		}
		return MessageAgentSwig.sendMessageDirectly(agentName
				, message.getClass().getSimpleName()
				, messageData);
	}
	
	@SuppressWarnings("rawtypes")
	private byte[] createMessageData(TBase message) {
		if (message == null) {
			throw new IllegalArgumentException("message should not be null");
		}
		
		byte[] messageData = ProtocolUtil.serialize2Bytes(FACTORY, message);
		if (messageData == null) {
			throw new IllegalStateException("unable to serialize message");
		}
		
		if (message.getClass().getSimpleName().length() + messageData.length > MAX_MESSAGE_PACKET_LENGTH) {
			throw new IllegalArgumentException("message is too big, after serialize should not > "
					+ MAX_MESSAGE_PACKET_LENGTH);
		}
		return messageData;
	}
	
	@Override
	public int onStartUp() {
		try {
			StartUpMode mode = consumer.onStartUp();
			if (mode == StartUpMode.CLEAR_QUEUE_INIT) {
				return MessageAgentBridgeConstants.STARTUPMODE_CLEAR_QUEUE_INIT;
			}
			return MessageAgentBridgeConstants.STARTUPMODE_RESERVE_QUEUE;
		} catch (Throwable e) {
			AppLog.e("onStartUpException, cause exited for app in native", e);
			throw e;
		}
	}
	
	@Override
	public int onConsumeMessage(String topic, byte[] data) {
		ConsumeResult result = ConsumeResult.CONSUME_RETRY;
		
		do {
			try {
				if (INIT_TOPIC.equals(topic)) {
					consumer.onInit();
					result = ConsumeResult.CONSUME_OK;
					break ;
				}
			
				ConsumeEntry entry = consumerCallbackMethodMap.get(topic);
				if (entry == null) {
					AppLog.i("failed to found consume method for " + topic + ", drop it");
					result = ConsumeResult.CONSUME_FAILED_DROP;
					break;
				}
				
				TBase message = entry.messageType.newInstance();
				try {
					TMemoryInputTransport input = new TMemoryInputTransport(data, 0, data.length);
					TProtocol protocol = FACTORY.getProtocol(input);
					message.read(protocol);
				} catch (Throwable e) {
					AppLog.e("unserialize " + topic + " message failed, drop it", e);
					result = ConsumeResult.CONSUME_FAILED_DROP;
				}
				
				result = ConsumeResult.class.cast(entry.consumerMethod.invoke(consumer, message));
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
				try {
					// let the process can resume, process may have bug
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} finally {
				if (AppLog.infoEnabled()) {
					AppLog.i("consume " + topic + " message, length=" + data.length + ", result=" + result);
				}
			}
		} while(false);
		
		if (result == ConsumeResult.CONSUME_OK) {
			return MessageAgentBridgeConstants.MESSAGE_CONSUME_OK;
		} else if (result == ConsumeResult.CONSUME_FAILED_DROP) {
			return MessageAgentBridgeConstants.MESSAGE_CONSUME_FAILED_DROP;
		} else {
			return MessageAgentBridgeConstants.MESSAGE_RETRY;
		}
	}
	
}
