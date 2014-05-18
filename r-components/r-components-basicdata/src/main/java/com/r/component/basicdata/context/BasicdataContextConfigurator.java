/**
 * 
 */
package com.r.component.basicdata.context;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;

/**
 * 基础数据配置文件
 * 
 * @author oky
 * 
 */
public class BasicdataContextConfigurator implements InitializingBean {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(BasicdataContextConfigurator.class);

	/** 基础数据执行器 */
	protected List<BasicdataExecutor> basicdataExecutors;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("加载基础数据配置文件文成....");
	}

	public List<BasicdataExecutor> getBasicdataExecutors() {
		return basicdataExecutors;
	}

	public void setBasicdataExecutors(List<BasicdataExecutor> basicdataExecutors) {
		this.basicdataExecutors = basicdataExecutors;
	}

}
