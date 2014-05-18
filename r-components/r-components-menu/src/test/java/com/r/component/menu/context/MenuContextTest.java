/**
 * 
 */
package com.r.component.menu.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.component.menu.Menu;
import com.r.component.menu.executor.xml.XmlMenu;
import com.r.core.util.XStreamUtil;

/**
 * 菜单测试
 * 
 * @author oky
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-menu.xml", })
public class MenuContextTest {

	@javax.annotation.Resource(name = "menuContext")
	private MenuContext menuContext;

	@Test
	public void test() throws Exception {
		String str = menuContext.getMenuHtml("main",null);
		System.out.println(str);
	}

	// @Test
	public void saveMenuTest() throws Exception {
		XmlMenu menu = new XmlMenu();
		menu.setExecjs("execjs");
		menu.setId("root");
		menu.setName("testmenu");
		// menu.setOtherAttribute(otherAttribute);
		menu.setUrl("url");

		List<XmlMenu> childs = new ArrayList<XmlMenu>();
		XmlMenu menu2 = new XmlMenu();
		XmlMenu menu3 = new XmlMenu();
		menu2.setId("22222");
		menu2.setName("222222");
		menu3.setId("3333");
		menu3.setName("333333");
		childs.add(menu2);
		childs.add(menu3);
		menu.setChildMenus(childs);

		Map<String, String> map = new HashMap<String, String>();
		map.put("css", "css");
		map.put("abcd", "efgh");
		menu.setOtherAttribute(map);

		String xml = XStreamUtil.toXml(menu);
		System.out.println(xml);
	}

	// @Test
	public void loadMenuTest() throws Exception {
		String xml = "<menu id=\"root\">  <otherAttribute>	    <property key=\"abcd\" value=\"efgh\"/>	    <property key=\"css\" value=\"css\"/>	  </otherAttribute>  <name>testmenu</name>  <url>url</url>  <execjs>execjs</execjs>  <menu id=\"22222\">    <name>222222</name>   </menu>  <menu id=\"3333\">    <name>333333</name> </menu></menu>";
		Menu fromXML = XStreamUtil.fromXML(XmlMenu.class, xml);

		System.out.println(XStreamUtil.toXml(fromXML));
	}
}
