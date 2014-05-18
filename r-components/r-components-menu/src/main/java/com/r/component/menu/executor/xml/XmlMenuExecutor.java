/**
 * 
 */
package com.r.component.menu.executor.xml;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.r.component.menu.Menu;
import com.r.component.menu.context.MenuExecutor;
import com.r.core.exceptions.io.IOReadErrorException;
import com.r.core.util.XStreamUtil;

/**
 * 菜单描述项
 * 
 * @author oky
 * 
 */
public class XmlMenuExecutor implements MenuExecutor {

	private String name; // 菜单名称
	private String menuLocation; // 菜单文件位置
	private String templateLocation; // 菜单模板位置

	@Override
	public String getMenuName() {
		Resource resource = new PathMatchingResourcePatternResolver().getResource(menuLocation);
		String resFilename = resource.getFilename();
		return resFilename.substring(0, resFilename.lastIndexOf('.'));
	}

	@Override
	public Menu getMenu() {
		Resource resource = new PathMatchingResourcePatternResolver().getResource(menuLocation);
		return resolverMenuFromeResource(resource);
	}

	@Override
	public String getMenuTemplate() {
		Resource resource = new PathMatchingResourcePatternResolver().getResource(templateLocation);
		return resolverMenuTemplateFromeResource(resource);
	}

	/**
	 * 根据资源文件解析菜单数据
	 * 
	 * @param resource
	 */
	private XmlMenu resolverMenuFromeResource(Resource resource) {
		String menuXml = null;
		XmlMenu menu = null;
		// 获得菜单xml
		try {
			menuXml = IOUtils.toString(resource.getInputStream());
		} catch (IOException e) {
			throw new IOReadErrorException("读取菜单XML失败 : {}", name);
		}
		// 解析菜单
		try {
			menu = XStreamUtil.fromXML(XmlMenu.class, menuXml);
		} catch (Exception e) {
			String errorMessage = MessageFormatter.format("解析菜单XML失败 : {}", name).getMessage();
			throw new IOReadErrorException(errorMessage, e);
		}

		return menu;
	}

	/**
	 * 根据资源文件解析菜单模板
	 * 
	 * @param resource
	 */
	private String resolverMenuTemplateFromeResource(Resource resource) {
		try {
			return IOUtils.toString(resource.getURI());
		} catch (IOException e) {
			throw new IOReadErrorException("菜单模板读取失败", e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenuLocation() {
		return menuLocation;
	}

	public void setMenuLocation(String menuLocation) {
		this.menuLocation = menuLocation;
	}

	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}
}
