/**
 * 
 */
package com.r.component.basicdata.executor.xml;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author Administrator
 * 
 */
public class XmlBasicdataConfigurator implements InitializingBean {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("MessageCenterContextConfigurator afterPropertiesSet..........");
	}

}
