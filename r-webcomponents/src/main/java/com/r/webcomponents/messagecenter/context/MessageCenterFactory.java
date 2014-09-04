/**
 * 
 */
package com.r.webcomponents.messagecenter.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 消息中心工厂类
 * 
 * @author Administrator
 *
 */
public class MessageCenterFactory extends MessageCenterContext implements
		FactoryBean<MessageCenterContext> {

	@Override
	public MessageCenterContext getObject() throws Exception {
		return MessageCenterContext.getContext();
	}

	@Override
	public Class<?> getObjectType() {
		return MessageCenterContext.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
