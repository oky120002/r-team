/**
 * 
 */
package com.r.component.menu.context;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;

/**
 * 菜单配置文件
 * 
 * @author oky
 * 
 */
public class MenuContextConfigurator implements InitializingBean {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(MenuContextConfigurator.class);

	/** 菜单执行器 */
	protected List<MenuExecutor> menuExecutors;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("afterPropertiesSet MenuContextConfigurator....................................");
	}

	public List<MenuExecutor> getMenuExecutors() {
		return menuExecutors;
	}

	public void setMenuExecutors(List<MenuExecutor> menuExecutors) {
		this.menuExecutors = menuExecutors;
	}

}
