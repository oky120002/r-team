/**
 * 
 */
package com.r.component.menu.context.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.r.component.menu.Menu;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

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
		if (CollectionUtils.isNotEmpty(childMenus)) {
			List<Menu> ms = new ArrayList<Menu>();
			for (Menu menu : childMenus) {
				ms.add(menu);
			}
			return ms;
		}
		return null;
	}

	public List<MenuXmlImpl> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<MenuXmlImpl> childMenus) {
		this.childMenus = childMenus;
	}

	@Override
	public String toString() {
		return "MenuXmlImpl [id=" + id + ", name=" + name + "]";
	}
}
