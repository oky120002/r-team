/**
 * 
 */
package com.r.component.basicdata.context;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;

import com.r.core.exceptions.initialization.SpringConfiguratorErrorException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 基础数据配置文件
 * 
 * @author oky
 * 
 */
public class BasicdataContextConfigurator implements InitializingBean {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 基础数据执行器 */
	private List<BasicdataExecutor> basicdataExecutors;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("BasicdataContextConfigurator afterPropertiesSet..........");

		if (CollectionUtils.isEmpty(basicdataExecutors)) {
			// XXX r-components-basicdata 需要修改,如果不提供,就使用默认执行器
			throw new SpringConfiguratorErrorException("请至少添加一个基础数据执行器");
		}
	}

	public List<BasicdataExecutor> getBasicdataExecutors() {
		return basicdataExecutors;
	}

	public void setBasicdataExecutors(List<BasicdataExecutor> basicdataExecutors) {
		this.basicdataExecutors = basicdataExecutors;
	}

}
