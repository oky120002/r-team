package com.r.component.menu;

import java.util.List;
import java.util.Map;

public interface Menu {

	/** 获得菜单唯一ID */
	String getId();

	/** 获得菜单名称 */
	String getName();

	/** 获得菜单跳转的url */
	String getUrl();

	/** 获得执行的js代码,getUrl()优先级高于js代码的执行 */
	String getExecjs();

	/** 菜单html控件的id */
	String getAttrid();

	/** 获得attr上的其它属性 */
	Map<String, String> getOtherAttribute();

	/** 获得子菜单 */
	List<Menu> getChilds();

	/** 此菜单能够正常输出html代码 */
	boolean isCanOutputHtml();

	/**
	 * 更新菜单的输出html功能<br />
	 * 如果传入空,则所有菜单都能输出html代码
	 * 
	 * @param canOutputMenuIds
	 *            能够输出html代码的菜单ID
	 */
	void updateCanOutput(String[] canOutputMenuIds);

	/** 必须实现菜单的深拷贝克隆 */
	Object clone() throws CloneNotSupportedException;
}