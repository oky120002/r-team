/**
 * 
 */
package com.r.component.messagecenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 具体消息体配置
 * 
 * @author Administrator
 * 
 */
public class MessageParserConfigurator implements InitializingBean {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 链接超时,单位/毫秒<br />
	 * 如果在配置文件中没有设置,就设置成默认值(继承子MessageCenterContextConfigurator中的超时时间)
	 */
	protected int timeout = -1;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("Init MessageParserConfigurator : {}", getClass().getName());
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

}
