/**
 * 
 */
package com.r.component.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Administrator
 *
 */
public class MenuImpl implements Cloneable {

	private String id; // 菜单唯一id
	private String name; // 菜单名称(显示在页面上的名称)
	private String url; // 点击菜单打开的页面url
	private String execjs; // 点击菜单执行的js(如果属性url不为空,则不执行此js)
	private String attrid; // 菜单html控件的id
	private Map<String, String> otherAttribute; // 菜单其它的自定义属性
	private List<MenuImpl> childMenus; // 子菜单(如果没有子菜单,则为叶子节点)
	private boolean canOutputHtml; // 是否输出html代码(此字段一般作用权限控制时,控制是否输出此菜单html代码)

	public MenuImpl(Menu menu) {
		super();
		this.id = menu.getId();
		this.name = menu.getName();
		this.url = menu.getUrl();
		this.execjs = menu.getExecjs();
		this.attrid = menu.getAttrid();

		this.otherAttribute = new HashMap<String, String>();
		Map<String, String> other = menu.getOtherAttribute();
		if (MapUtils.isNotEmpty(other)) {
			this.otherAttribute.putAll(menu.getOtherAttribute());
		}

		this.childMenus = new ArrayList<MenuImpl>();
		List<Menu> childs = menu.getChilds();
		if (CollectionUtils.isNotEmpty(childs)) {
			for (Menu m : childs) {
				this.childMenus.add(new MenuImpl(m));
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExecjs() {
		return execjs;
	}

	public void setExecjs(String execjs) {
		this.execjs = execjs;
	}

	public String getAttrid() {
		return attrid;
	}

	public void setAttrid(String attrid) {
		this.attrid = attrid;
	}

	public Map<String, String> getOtherAttribute() {
		return otherAttribute;
	}

	public void setOtherAttribute(Map<String, String> otherAttribute) {
		this.otherAttribute = otherAttribute;
	}

	public List<MenuImpl> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<MenuImpl> childMenus) {
		this.childMenus = childMenus;
	}

	public boolean isCanOutputHtml() {
		return canOutputHtml;
	}

	public void setCanOutputHtml(boolean canOutputHtml) {
		this.canOutputHtml = canOutputHtml;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		MenuImpl menu = (MenuImpl) super.clone();
		// 处理List和自身变量的浅拷贝
		// menu.parentMenu = null; // 清空父级关联
		if (CollectionUtils.isNotEmpty(this.childMenus)) {
			menu.setChildMenus(new ArrayList<MenuImpl>());

			for (MenuImpl m : this.childMenus) {
				MenuImpl m2 = (MenuImpl) m.clone();
				// m2.parentMenu = menu;
				menu.childMenus.add(m2);
			}
		}
		return menu;
	}

	/** 更新重构能够输出html代码的菜单 */
	public void updateCanOutput(String[] canOutputMenuIds) {

		if (ArrayUtils.isEmpty(canOutputMenuIds) || ArrayUtils.contains(canOutputMenuIds, this.id)) {
			this.canOutputHtml = true;
		} else {
			this.canOutputHtml = false;
		}

		if (CollectionUtils.isNotEmpty(this.childMenus)) {
			for (MenuImpl m : this.childMenus) {
				m.updateCanOutput(canOutputMenuIds);
			}
		}
	}

	@Override
	public String toString() {
		return "MenuImpl [id=" + id + ", name=" + name + "]";
	}
}
