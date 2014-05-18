/**
 * 
 */
package com.r.component.basicdata.context;

import org.springframework.beans.factory.InitializingBean;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;

/**
 * 基础数据Context
 * 
 * @author oky
 * 
 */
public class BasicdataContext extends BasicdataContextConfigurator implements InitializingBean {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(BasicdataContext.class);

	/** 菜单Context的唯一实例 */
	private static BasicdataContext context = null; // 对自身的引用

	/** 获取菜单Context唯一实体 */
	public static BasicdataContext getContext() {
		return context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		logger.info("Init BasicdataContext................");
		context = this;

	}

}
