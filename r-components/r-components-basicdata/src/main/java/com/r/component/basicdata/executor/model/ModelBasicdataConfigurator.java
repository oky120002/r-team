/**
 * 
 */
package com.r.component.basicdata.executor.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author Administrator
 * 
 */
public class ModelBasicdataConfigurator implements InitializingBean {
	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 数据源 */
	private DataSource dataSource;

	/** 事务处理器 */
	private PlatformTransactionManager platformTransactionManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("ModelBasicdataConfigurator afterPropertiesSet..........");

	}

	
}
