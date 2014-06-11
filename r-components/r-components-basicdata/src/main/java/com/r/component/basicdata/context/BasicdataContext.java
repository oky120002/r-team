/**
 * 
 */
package com.r.component.basicdata.context;

import org.springframework.beans.factory.InitializingBean;

/**
 * 基础数据Context
 * 
 * @author oky
 * 
 */
public class BasicdataContext extends BasicdataContextConfigurator implements InitializingBean {

	/** 菜单Context的唯一实例 */
	private static BasicdataContext context = null; // 对自身的引用

	/** 获取菜单Context唯一实体 */
	public static BasicdataContext getContext() {
		return context;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		context = this;

	}
}
