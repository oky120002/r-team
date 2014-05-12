/**
 * 
 */
package com.r.component.menu.context;

/**
 * 菜单描述项
 * 
 * @author oky
 * 
 */
public class MenuDescription {
	private String name; // 菜单名称
	private String menuLocation; // 菜单文件位置
	private String templateLocation; // 菜单模板位置

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
