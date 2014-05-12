/**
 * 
 */
package com.r.component.menu;

import java.util.List;
import java.util.Map;

import com.r.component.menu.bean.MapCustomConverter;
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
public class Menu {

	@XStreamAsAttribute
	private String id; // 菜单唯一id
	@XStreamAlias(value = "name")
	private String name; // 菜单名称(显示在页面上的名称)
	@XStreamAlias(value = "url")
	private String url; // 点击菜单打开的页面url
	@XStreamAlias(value = "execjs")
	private String execjs; // 点击菜单执行的js(如果属性url不为空,则不执行此js)

	@XStreamAlias(value = "attrid")
	private String attrid;
	@XStreamAlias(value = "attrclass")
	private String attrclass;

	 @XStreamConverter(value = MapCustomConverter.class)
	 @XStreamAlias("otherAttribute")
	 private Map<String, String> otherAttribute; // 菜单其它的自定义属性

	@XStreamOmitField
	private Menu parent; // 父菜单(如果没有父菜单,则为顶层节点)

	@XStreamImplicit(itemFieldName = "menu")
	private List<Menu> childs; // 子菜单(如果没有子菜单,则为叶子节点)

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

	public String getAttrclass() {
		return attrclass;
	}

	public void setAttrclass(String attrclass) {
		this.attrclass = attrclass;
	}

	 public Map<String, String> getOtherAttribute() {
	 return otherAttribute;
	 }
	
	 public void setOtherAttribute(Map<String, String> otherAttribute) {
	 this.otherAttribute = otherAttribute;
	 }

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChilds() {
		return childs;
	}

	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + "]";
	}
}
