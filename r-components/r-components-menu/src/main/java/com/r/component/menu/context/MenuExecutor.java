/**
 * 
 */
package com.r.component.menu.context;

import com.r.component.menu.Menu;


/**
 * 可以实现此接口,从数据库,网络,外部文件中获取菜单的定义.
 * 
 * @author oky
 * 
 */
public interface MenuExecutor {
	/** 获取菜单名称 */
	String getMenuName();

	/** 获取菜单实体 */
	Menu getMenu();

	/** 获取菜单模板 */
	String getMenuTemplate();
}