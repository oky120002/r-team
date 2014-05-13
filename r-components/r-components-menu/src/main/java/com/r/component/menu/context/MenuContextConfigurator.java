/**
 * 
 */
package com.r.component.menu.context;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.component.menu.MenuDescription;

/**
 * 菜单配置文件
 * 
 * @author oky
 * 
 */
public class MenuContextConfigurator implements InitializingBean {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(MenuContextConfigurator.class);

	/** 菜单描述 */
	protected List<MenuDescription> menuDescriptions;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("加载菜单文件完成");
	}

	public List<MenuDescription> getMenuDescriptions() {
		return menuDescriptions;
	}

	public void setMenuDescriptions(List<MenuDescription> menuDescriptions) {
		this.menuDescriptions = menuDescriptions;
	}

}
