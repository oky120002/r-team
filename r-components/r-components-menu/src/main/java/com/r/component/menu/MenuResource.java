/**
 * 
 */
package com.r.component.menu;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.io.Resource;

import com.r.core.exceptions.io.ResourceIOErrorException;
import com.r.core.util.XStreamUtil;

/**
 * 菜单资源
 * 
 * @author oky
 * 
 */
public class MenuResource {
	private String name; // 菜单名称
	private Menu menu; // 菜单
	private String template; // 菜单模板

	/**
	 * 根据资源文件解析菜单数据
	 * 
	 * @param resource
	 */
	public void resolverMenuResource(Resource resource) {
		String menuXml = null;
		Menu menu = null;
		// 获得菜单xml
		try {
			menuXml = IOUtils.toString(resource.getInputStream());
		} catch (IOException e) {
			throw new ResourceIOErrorException("读取菜单XML失败 : {}", name);
		}
		// 解析菜单
		try {
			menu = XStreamUtil.fromXML(Menu.class, menuXml);
		} catch (Exception e) {
			String errorMessage = MessageFormatter.format("解析菜单XML失败 : {}", name).getMessage();
			throw new ResourceIOErrorException(errorMessage, e);
		}
		// 设置菜单父子关系
		setMenuPraentAndChildRelation(menu);

		setMenu(menu);
	}

	/**
	 * 根据资源文件解析模板数据
	 * 
	 * @param resource
	 */
	public void resolverTemplateResource(Resource resource) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 获得菜单的html代码
	 * 
	 * @param outputDisableMenuIds
	 *            禁止输出的菜单ID
	 * @return html
	 */
	public String getHtml(String[] outputDisableMenuIds) {
		return null;
	}

	/**
	 * 设置菜单父子关系
	 * 
	 * @param menu
	 */
	private void setMenuPraentAndChildRelation(Menu menu) {
		List<Menu> childs = menu.getChilds();
		if (CollectionUtils.isEmpty(childs)) {
			return;
		}
		for (Menu cm : childs) {
			cm.setParent(menu);
			setMenuPraentAndChildRelation(cm);
		}
	}

	@Override
	public String toString() {
		return "MenuResource[" + name + "]";
	}
}
