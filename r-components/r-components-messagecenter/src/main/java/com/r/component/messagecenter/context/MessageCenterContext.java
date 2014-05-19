/**
 * 
 */
package com.r.component.messagecenter.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

import com.r.component.messagecenter.Message;
import com.r.component.messagecenter.MessageInterceptor;
import com.r.component.messagecenter.MessageParser;
import com.r.component.messagecenter.exception.ErrorMessageException;
import com.r.component.messagecenter.exception.ErrorMessageParserException;
import com.r.component.messagecenter.exception.ErrorProtocolException;
import com.r.component.messagecenter.exception.MessageCenterException;

/**
 * 消息中心容器
 * 
 * @author Administrator
 * 
 */
public class MessageCenterContext extends MessageCenterContextConfigurator implements InitializingBean {
	/** 消息中心的唯一实例 */
	private static MessageCenterContext context = null; // 对自身的引用

	/** 消息解析器缓存 */
	private Map<String, MessageParser> cacheMessageParsers = new HashMap<String, MessageParser>();
	/** 消息Bean缓存 */
	private Map<String, Message> cacheMessageBeans = new HashMap<String, Message>();

	/** 线程池 */
	private static ExecutorService executorService = null;

	protected MessageCenterContext() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		context = this;

		// 设置消息解析器缓存
		List<MessageParser> messagesParsers = getMessagesParsers();
		if (messagesParsers == null) {
			logger.info("message parser is empty!");
		} else {
			for (MessageParser messagesParser : messagesParsers) {
				String messageType = messagesParser.getMessageType();
				if (cacheMessageParsers.containsKey(messageType)) {
					throw new ErrorMessageParserException("exist repeat MessageParser : [" + messageType + "]");
				}
				cacheMessageParsers.put(messageType, messagesParser);
			}
		}

		// 设置消息Bean缓存
		List<Message> messages = getMessages();
		if (messages == null) {
			// 程序不会走到此处,在super.afterPropertiesSet()中,有给此map设置默认值
			throw new ErrorMessageException("MessageBean is empty!");
		} else {
			for (Message message : messages) {
				String messageType = message.getMessageType();
				if (cacheMessageBeans.containsKey(messageType)) {
					throw new ErrorMessageParserException("exist repeat MessageBean : [" + messageType + "]");
				}
				cacheMessageBeans.put(message.getMessageType(), message);
			}
		}

		// 设置线程池
		boolean asynchronous = isAsynchronous();
		if (asynchronous) { // 如果开启异步发送消息则初始化线程池
			int maxThreads = getMaxThreads();
			if (maxThreads > 0) {
				executorService = Executors.newFixedThreadPool(maxThreads);
			} else {
				executorService = Executors.newCachedThreadPool();
			}
		}
	}

	/**
	 * 获取消息中心唯一实体
	 * 
	 * @return
	 */
	public static MessageCenterContext getContext() {
		return context;
	}

	/**
	 * 发送消息
	 * 
	 * @param protocol
	 *            消息协议
	 * @param objects
	 *            消息内容
	 */
	public void sendMessage(String protocol, Object... objects) {
		String messageType = resolveMessageTypeFromProtocol(protocol);
		Message message = getMessageByType(messageType);

		// 设置消息Bean必要值
		message.setProtocol(protocol.substring(MessageCenterContextConfigurator.MESSAGE_PROTOCOL_PREFIX.length()));
		message.setContent(objects);

		boolean asynchronous = isAsynchronous();
		if (asynchronous) {
			sendMessageByAsynchronous(message);
		} else {
			sendMessage(message);
		}
	}

	/**
	 * 根据消息类型返回消息Bean,如果没有找到则返回默认的消息Bean<br />
	 * 所有返回的消息Bean都不是原始的消息Bean,全部都是clone出来的备份
	 * 
	 * @param messageType
	 *            消息类型
	 * @return 消息Bean
	 */
	public Message getMessageByType(String messageType) {
		// 判断是否存在此消息的解析器
		if (!cacheMessageParsers.containsKey(messageType)) {
			throw new ErrorMessageParserException("MessageParser is null : [" + messageType + "]");
		}

		Message message = null;
		// 先判断是否已经存在此类型的消息Bean,如果不存在则返回默认的消息Bean
		if (cacheMessageBeans.containsKey(messageType)) {
			message = cacheMessageBeans.get(messageType);
		} else {
			message = cacheMessageBeans.get(MessageCenterContextConfigurator.MESSAGE_TYPE_DEFAULT);
		}
		// clone一份消息Bean
		try {
			message = (Message) BeanUtils.cloneBean(message);
		} catch (Exception e) {
			throw new ErrorMessageException("Message is newInstance error!");
		}
		return message;
	}

	/** 异步发送消息 */
	private void sendMessageByAsynchronous(final Message message) {
		executorService.execute(new Runnable() {
			public void run() {
				sendMessage(message);
			}
		});
	}

	/** 发送消息 */
	private void sendMessage(final Message message) {
		String messageType = message.getMessageType();

		MessageInterceptor messageInterceptor = getMessageInterceptor();
		if (messageInterceptor != null) {
			messageInterceptor.beforeMessageSend(message);
		}

		MessageParser messageParser = cacheMessageParsers.get(messageType);
		MessageCenterException messageCenterException = null;
		try {
			messageParser.sendMessage(message);
		} catch (MessageCenterException e) {
			messageCenterException = e;
		}

		if (messageInterceptor != null) {
			messageInterceptor.afterMessageSend(message, messageCenterException);
			if (messageCenterException != null) {
				throw messageCenterException;
			}
		}
	}

	/***
	 * 从原始协议字符串中解析出已经注册的消息BeanType
	 * 
	 * @param protocol
	 *            格式: "mc://{type}/{protocol}"
	 * @return
	 */
	private String resolveMessageTypeFromProtocol(String protocol) {
		if (protocol == null || protocol.trim().length() < MessageCenterContextConfigurator.MESSAGE_PROTOCOL_PREFIX.length() + 2) {
			throw new ErrorProtocolException("protocol is error (protocol is empty) : " + protocol);
		}
		if (!protocol.startsWith(MessageCenterContextConfigurator.MESSAGE_PROTOCOL_PREFIX)) {
			throw new ErrorProtocolException("protocol is error (protocol's prefix is error) : " + protocol);
		}
		protocol = protocol.substring(MessageCenterContextConfigurator.MESSAGE_PROTOCOL_PREFIX.length());
		String messageType = protocol.split("/")[0].trim();
		if (messageType.length() == 0) {
			throw new ErrorProtocolException("protocol is error (message type is empty) : " + protocol);
		}
		return messageType;
	}
}
