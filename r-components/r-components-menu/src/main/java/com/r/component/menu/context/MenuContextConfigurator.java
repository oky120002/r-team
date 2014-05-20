/**
 * 
 */
package com.r.component.menu.context;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 菜单配置文件
 * 
 * @author oky
 * 
 */
public class MenuContextConfigurator implements InitializingBean {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 菜单执行器 */
	private List<MenuExecutor> menuExecutors;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("MenuContextConfigurator afterPropertiesSet..........");
		
	}

	public List<MenuExecutor> getMenuExecutors() {
		return menuExecutors;
	}

	public void setMenuExecutors(List<MenuExecutor> menuExecutors) {
		this.menuExecutors = menuExecutors;
	}

}
