/**
 * 
 */
package com.r.component.menu.context;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.component.menu.Menu;
import com.r.core.exceptions.CloneErrorException;
import com.r.core.exceptions.io.IOReadErrorException;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 菜单Context
 * 
 * @author oky
 * 
 */
public class MenuContext extends MenuContextConfigurator implements InitializingBean {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(MenuContext.class);

	/** 菜单Context的唯一实例 */
	private static MenuContext context = null; // 对自身的引用

	/** freemarker模板引擎配置核心 */
	private Configuration freemarkerConfiguration = null;

	/** 菜单资源 */
	private Map<String, Menu> menus;

	/** 获取菜单Context唯一实体 */
	public static MenuContext getContext() {
		return context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		logger.info("Init MenuContext");
		context = this;
		menus = new HashMap<String, Menu>();
		freemarkerConfiguration = new Configuration();

		// 获取menu资源文件
		if (CollectionUtils.isNotEmpty(super.menuDescriptions)) {
			StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();// 字符串模板加载器
			for (MenuDescription menuDescription : super.menuDescriptions) {
				// 校验菜单是否重复
				if (menus.containsKey(menuDescription.getMenuName())) {
					throw new IOReadErrorException("菜单重复 : {}", menuDescription.getMenuName());
				}
				stringTemplateLoader.putTemplate(menuDescription.getMenuName(), menuDescription.getMenuTemplate());
				menus.put(menuDescription.getMenuName(), menuDescription.getMenu());
			}
			freemarkerConfiguration.setTemplateLoader(stringTemplateLoader);
			freemarkerConfiguration.setObjectWrapper(new DefaultObjectWrapper());
		}
	}

	/**
	 * 获得根据菜单模板和禁止输出的菜单生成的菜单的html代码
	 * 
	 * @param menuName
	 *            菜单名称
	 * @param canOutputMenuIds
	 *            能够输出html代码的菜单ID集合,如果为空,则输出所有菜单
	 * @return 菜单的html代码
	 */
	public String getMenuHtml(String menuName, String[] canOutputMenuIds) {
		// 在线程变量中查找菜单
		if (menus.containsKey(menuName)) {
			try {
				// XXX r-component-menu 这里是每次都要解析菜单.不够效率.需要创建一个缓存机制,等待修改
				Menu curr = (Menu) menus.get(menuName).clone();
				curr.updateCanOutput(canOutputMenuIds);

				Template template = null;
				try {
					template = freemarkerConfiguration.getTemplate(menuName);
				} catch (IOException e) {
					throw new IOReadErrorException("菜单模板读取错误", e);
				}
				if (template == null) {
					return "";
				}

				Writer writer = new StringWriter();
				try {
					template.process(curr, writer);
					writer.flush();
					return writer.toString();
				} catch (TemplateException e) {
					throw new IOReadErrorException("菜单模板解析错误", e);
				} catch (IOException e) {
					throw new IOReadErrorException("菜单模板读取错误", e);
				} finally {
					IOUtils.closeQuietly(writer);
				}
			} catch (CloneNotSupportedException e) {
				throw new CloneErrorException("克隆菜单失败!", e);
			}
		} else {
			// App中没有此菜单,返回""
			return "";
		}
	}

	/**
	 * 获得菜单模板
	 * 
	 * @param menuName
	 *            菜单名称
	 * @return 菜单模板
	 */
	public String getMenuTemplate(String menuName) {
		try {
			return String.valueOf(freemarkerConfiguration.getTemplateLoader().findTemplateSource(menuName));
		} catch (IOException e) {
			throw new IOReadErrorException("菜单模板读取错误", e);
		}
	}

	/**
	 * 设置菜单模板
	 * 
	 * @param menuName
	 *            菜单名称
	 * @param template
	 *            菜单模板
	 */
	public void setMenuTemplate(String menuName, String template) {
		StringTemplateLoader templateLoader = (StringTemplateLoader) freemarkerConfiguration.getTemplateLoader();
		templateLoader.putTemplate(menuName, template);
	}
}
