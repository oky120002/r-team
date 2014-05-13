package com.r.component.menu;

import java.util.List;
import java.util.Map;

/**
 * 菜单接口
 * 
 * @author Administrator
 *
 */
public interface Menu {

	/** 获得菜单唯一ID */
	String getId();

	/** 获得菜单名称 */
	String getName();

	/** 获得菜单跳转的url */
	String getUrl();

	/** 获得执行的js代码 */
	String getExecjs();

	/** 菜单html控件的id */
	String getAttrid();

	/** 获得attr上的其它属性 */
	Map<String, String> getOtherAttribute();

	/** 获得子菜单 */
	List<Menu> getChilds();
}