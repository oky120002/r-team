/**
 * 
 */
package com.r.component.menu.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 菜单工厂
 * 
 * @author oky
 * 
 */
public class MenuFactory extends MenuContext implements FactoryBean<MenuContext> {
	@Override
	public MenuContext getObject() throws Exception {
		return MenuContext.getContext();
	}

	@Override
	public Class<?> getObjectType() {
		return MenuContext.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
