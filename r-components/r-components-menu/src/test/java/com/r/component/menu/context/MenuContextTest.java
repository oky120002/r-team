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
		Menu menu = menuContext.getMenu("main");
		System.out.println(menu);
	}

	// @Test
	public void saveMenuTest() throws Exception {
		Menu menu = new Menu();
		menu.setAttrclass("classroot");
		menu.setAttrid("idroot");
		menu.setExecjs("execjs");
		menu.setId("root");
		menu.setName("testmenu");
		// menu.setOtherAttribute(otherAttribute);
		menu.setUrl("url");

		List<Menu> childs = new ArrayList<Menu>();
		Menu menu2 = new Menu();
		Menu menu3 = new Menu();
		menu2.setId("22222");
		menu2.setName("222222");
		menu2.setParent(menu);
		menu3.setId("3333");
		menu3.setName("333333");
		menu3.setParent(menu);
		childs.add(menu2);
		childs.add(menu3);
		menu.setChilds(childs);

		Map<String, String> map = new HashMap<String, String>();
		map.put("css", "css");
		map.put("abcd", "efgh");
		menu.setOtherAttribute(map);

		String xml = XStreamUtil.toXml(menu);
		System.out.println(xml);
	}

	// @Test
	public void loadMenuTest() throws Exception {
		String xml = "<menu id=\"root\">  <otherAttribute>	    <property key=\"abcd\" value=\"efgh\"/>	    <property key=\"css\" value=\"css\"/>	  </otherAttribute>  <name>testmenu</name>  <url>url</url>  <execjs>execjs</execjs>  <attrid>idroot</attrid>  <attrclass>classroot</attrclass>  <menu id=\"22222\">    <name>222222</name>   </menu>  <menu id=\"3333\">    <name>333333</name> </menu></menu>";
		Menu fromXML = XStreamUtil.fromXML(Menu.class, xml);

		System.out.println(XStreamUtil.toXml(fromXML));
	}
}
