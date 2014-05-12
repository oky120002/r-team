/**
 * 
 */
package com.r.component.menu.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.component.menu.Menu;
import com.r.component.menu.MenuResource;
import com.r.core.exceptions.io.ResourceIOErrorException;

/**
 * 菜单Context
 * 
 * @author oky
 * 
 */
public class MenuContext extends MenuContextConfigurator implements InitializingBean {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(MenuContext.class);

	/** 菜单Context的唯一实例 */
	private static MenuContext context = null; // 对自身的引用

	/** 菜单资源 */
	private Map<String, MenuResource> menuResources;

	/** 获取菜单Context唯一实体 */
	public static MenuContext getContext() {
		return context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		logger.info("Init MenuContext");
		context = this;
		menuResources = new HashMap<String, MenuResource>();

		// 获取menu资源文件
		if (CollectionUtils.isNotEmpty(super.menuDescriptions)) {
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			for (MenuDescription menuDescription : super.menuDescriptions) {
				// 获取菜单名称
				Resource mRes = resolver.getResource(menuDescription.getMenuLocation());
				String resFilename = mRes.getFilename();
				String menuName = resFilename.substring(0, resFilename.lastIndexOf('.'));

				// 校验菜单是否重复
				if (menuResources.containsKey(menuName)) {
					throw new ResourceIOErrorException("菜单重复 : {}", menuName);
				}
				
				MenuResource menuResource = new MenuResource();
				// 设置菜单名称
				menuResource.setName(menuName);
				// 设置菜单
				logger.debug("解析菜单 : {}", menuName);
				menuResource.resolverMenuResource(mRes);
				// 设置菜单模板
				logger.debug("解析菜单模板 : {}", menuName);
				if (StringUtils.isNotBlank(menuDescription.getTemplateLocation())) {
					Resource tRes = resolver.getResource(menuDescription.getTemplateLocation());
					menuResource.resolverTemplateResource(tRes);
				}
				menuResources.put(menuName, menuResource);
			}
		}
	}

	/**
	 * 获得根据菜单模板和禁止输出的菜单生成的菜单的html代码
	 * 
	 * @param name
	 *            菜单名称
	 * @param outputDisableMenuIds
	 *            禁止输出的菜单ID
	 * @return 菜单的html代码
	 */
	public String getMenuHtml(String name, String[] outputDisableMenuIds) {
		if (menuResources.containsKey(name)) {
			return menuResources.get(name).getHtml(outputDisableMenuIds);
		}
		return null;
	}

	/**
	 * 获得菜单实体
	 * 
	 * @param name
	 *            菜单名称
	 * @return 菜单实体
	 */
	public Menu getMenu(String name) {
		if (menuResources.containsKey(name)) {
			return menuResources.get(name).getMenu();
		}
		return null;
	}

}
