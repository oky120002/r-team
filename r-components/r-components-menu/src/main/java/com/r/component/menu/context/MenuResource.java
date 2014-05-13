/**
 * 
 */
package com.r.component.menu.context;

import com.r.component.menu.Menu;

/**
 * 菜单资源
 * 
 * @author oky
 * 
 */
public class MenuResource {
	private String menuName; // 菜单名称
	private Menu menu; // 菜单
	private String menuTemplate; // 菜单模板

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMenuTemplate() {
		return menuTemplate;
	}

	public void setMenuTemplate(String menuTemplate) {
		this.menuTemplate = menuTemplate;
	}

	@Override
	public String toString() {
		return "MenuResource[" + menuName + "]";
	}
}
