/**
 * 
 */
package com.r.component.messagecenter;

import com.r.component.messagecenter.exception.MessageCenterException;

/**
 * 消息拦截器
 * 
 * @author oky
 * 
 */
public interface MessageInterceptor {

	/** 消息发送前 */
	void beforeMessageSend(Message message);

	/**
	 * 消息发送后
	 * 
	 * @param message
	 *            消息Bean
	 * @param e
	 *            错误信息,如果消息发送没有错误则传入null
	 * @return 如果返回true,则不进行异常处理,继续执行,否则会抛出MessageCenterException异常
	 */
	void afterMessageSend(Message message, MessageCenterException e);
}
