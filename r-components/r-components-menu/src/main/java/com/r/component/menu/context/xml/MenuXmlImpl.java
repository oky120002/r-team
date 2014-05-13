/**
 * 
 */
package com.r.component.menu.context.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.r.component.menu.Menu;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 菜单
 * 
 * @author oky
 * 
 */
@XStreamAlias("menu")
public class MenuXmlImpl implements Cloneable, Menu {

	@XStreamAsAttribute
	private String id; // 菜单唯一id
	@XStreamAlias(value = "name")
	private String name; // 菜单名称(显示在页面上的名称)
	@XStreamAlias(value = "url")
	private String url; // 点击菜单打开的页面url
	@XStreamAlias(value = "execjs")
	private String execjs; // 点击菜单执行的js(如果属性url不为空,则不执行此js)
	@XStreamAlias(value = "attrid")
	private String attrid; // 菜单html控件的id

	@XStreamConverter(value = MapCustomConverter.class)
	@XStreamAlias("otherAttribute")
	private Map<String, String> otherAttribute; // 菜单其它的自定义属性

	@XStreamImplicit(itemFieldName = "menu")
	private List<MenuXmlImpl> childMenus; // 子菜单(如果没有子菜单,则为叶子节点)

	@XStreamOmitField
	private boolean canOutputHtml; // 是否输出html代码(此字段一般作用权限控制时,控制是否输出此菜单html代码)

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getExecjs() {
		return execjs;
	}

	public void setExecjs(String execjs) {
		this.execjs = execjs;
	}

	@Override
	public String getAttrid() {
		return attrid;
	}

	public void setAttrid(String attrid) {
		this.attrid = attrid;
	}

	@Override
	public Map<String, String> getOtherAttribute() {
		return otherAttribute;
	}

	public void setOtherAttribute(Map<String, String> otherAttribute) {
		this.otherAttribute = otherAttribute;
	}

	@Override
	public List<Menu> getChilds() {
		List<Menu> ll = new ArrayList<Menu>();
		for (Menu menu : childMenus) {
			ll.add(menu);
		}
		return ll;
	}

	public List<MenuXmlImpl> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<MenuXmlImpl> childMenus) {
		this.childMenus = childMenus;
	}

	@Override
	public boolean isCanOutputHtml() {
		return canOutputHtml;
	}

	public void setCanOutputHtml(boolean canOutputHtml) {
		this.canOutputHtml = canOutputHtml;
	}

	/** 更新重构能够输出html代码的菜单 */
	@Override
	public void updateCanOutput(String[] canOutputMenuIds) {

		if (ArrayUtils.isEmpty(canOutputMenuIds) || ArrayUtils.contains(canOutputMenuIds, this.id)) {
			this.canOutputHtml = true;
		} else {
			this.canOutputHtml = false;
		}

		if (CollectionUtils.isNotEmpty(this.childMenus)) {
			for (MenuXmlImpl m : this.childMenus) {
				m.updateCanOutput(canOutputMenuIds);
			}
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		MenuXmlImpl menu = (MenuXmlImpl) super.clone();

		// 处理List和自身变量的浅拷贝
		// menu.parentMenu = null; // 清空父级关联
		if (CollectionUtils.isNotEmpty(this.childMenus)) {
			menu.setChildMenus(new ArrayList<MenuXmlImpl>());

			for (MenuXmlImpl m : this.childMenus) {
				MenuXmlImpl m2 = (MenuXmlImpl) m.clone();
				// m2.parentMenu = menu;
				menu.childMenus.add(m2);
			}
		}
		return menu;
	}

	@Override
	public String toString() {
		return "MenuXmlImpl [id=" + id + ", name=" + name + "]";
	}
}
