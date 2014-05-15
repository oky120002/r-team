/**
 * 
 */
package com.r.component.messagecenter.context;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.r.component.messagecenter.Message;
import com.r.component.messagecenter.MessageDefaultImpl;
import com.r.component.messagecenter.MessageInterceptor;
import com.r.component.messagecenter.MessageParser;
import com.r.component.messagecenter.MessageParserConfigurator;
import com.r.component.messagecenter.exception.ErrorMessageParserException;

/**
 * 消息中心基础配置<br/>
 * 
 * @author Administrator
 * 
 */
public class MessageCenterContextConfigurator implements InitializingBean {
	public static final String MESSAGE_PROTOCOL_PREFIX = "mc://"; // 协议头
	public static final String MESSAGE_TYPE_DEFAULT = "default"; // 消息默认key
	public static final String MESSAGE_TYPE_RTX = "rtx"; // rtx消息key
	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 消息发送超时,单位/毫秒,默认10,000毫秒 */
	protected int timeout = 10 * 1000;

	/** 消息是否异步执行,默认关闭 */
	protected boolean asynchronous = true;
	/**
	 * 异步执行时最大线程数,默认最大线程为5 ,且不能为负数<br />
	 * 如果填写正整数, 则创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。<br />
	 * 如果填写非正数(包括0),则创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。<br />
	 */
	protected int maxThreads = 5;

	/** 消息拦截器 */
	protected MessageInterceptor messageInterceptor;

	/** 消息解析器组 */
	protected List<MessageParser> messagesParsers;

	/** 消息Bean */
	protected List<Message> messages;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("Init MessageCenterContextConfigurator");

		// 设置消息Bean默认实现
		if (messages == null) {
			messages = new ArrayList<Message>();
		}
		if (!messages.contains(MESSAGE_TYPE_DEFAULT)) { // 如果没有被设置了默认实现(从xml配置文件中被设置)
			logger.info("Put MessageDefaultKey Class");
			messages.add(new MessageDefaultImpl());
		} else {
			// 覆盖默认的消息Bean实现
			logger.info("Cover MessageDefaultKey Class");
		}

		// 设置消息解析器组属性的默认值
		for (MessageParser messageParser : messagesParsers) {
			if (messageParser instanceof MessageParserConfigurator) {
				MessageParserConfigurator conf = (MessageParserConfigurator) messageParser;
				if (conf.getTimeout() < 0) {
					conf.setTimeout(timeout);
				}
			} else {
				throw new ErrorMessageParserException("MessageParser did not inherit MessageParserConfigurator");
			}
		}
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the asynchronous
	 */
	public boolean isAsynchronous() {
		return asynchronous;
	}

	/**
	 * @param asynchronous
	 *            the asynchronous to set
	 */
	public void setAsynchronous(boolean asynchronous) {
		this.asynchronous = asynchronous;
	}

	/**
	 * @return the maxThreads
	 */
	public int getMaxThreads() {
		return maxThreads;
	}

	/**
	 * @param maxThreads
	 *            the maxThreads to set
	 */
	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	/**
	 * @return the messagesParsers
	 */
	public List<MessageParser> getMessagesParsers() {
		return messagesParsers;
	}

	/**
	 * @param messagesParsers
	 *            the messagesParsers to set
	 */
	public void setMessagesParsers(List<MessageParser> messagesParsers) {
		this.messagesParsers = messagesParsers;
	}

	/**
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	/**
	 * @return the messageInterceptor
	 */
	public MessageInterceptor getMessageInterceptor() {
		return messageInterceptor;
	}

	/**
	 * @param messageInterceptor
	 *            the messageInterceptor to set
	 */
	public void setMessageInterceptor(MessageInterceptor messageInterceptor) {
		this.messageInterceptor = messageInterceptor;
	}

}
