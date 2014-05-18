/**
 * 
 */
package com.r.component.basicdata.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 基础数据工厂
 * 
 * @author oky
 * 
 */
public class BasicdataFactory extends BasicdataContext implements FactoryBean<BasicdataContext> {
	@Override
	public BasicdataContext getObject() throws Exception {
		return BasicdataContext.getContext();
	}

	@Override
	public Class<?> getObjectType() {
		return BasicdataContext.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
