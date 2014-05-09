/**
 * 
 */
package com.r.web.support.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;

/**
 * 支持视图控制器
 * 
 * @author oky
 * 
 */
@Controller
@RequestMapping(value = "/")
public class SupportControl {

	private static final Logger logger = LoggerFactory.getLogger(SupportControl.class);

	/**
	 * 登陆页面
	 * 
	 * @param model
	 * @return 页面路径
	 */
	@RequestMapping(value = "login")
	public String pageLogin(ModelMap model) {
		logger.debug("进入login");
		return "support/login";
	}

	/**
	 * 主页
	 * 
	 * @param model
	 * @return 页面路径
	 */
	public String mainLogin(ModelMap model) {
		logger.debug("进入main");
		return "support/main";
	}

	/**
	 * 权限拒绝页面
	 * 
	 * @param model
	 * @return 页面路径
	 */
	@RequestMapping(value = "denied")
	public String pageDenied(ModelMap model) {
		logger.debug("进入denied");
		return "support/denied";
	}

	/**
	 * 系统初始化页面
	 * 
	 * @param model
	 * @return 页面路径
	 */
	@RequestMapping(value = "init")
	public String pageInit(ModelMap model) {
		logger.debug("进入init");
		return "support/init";
	}

	/**
	 * 退出页面
	 * 
	 * @param model
	 * @return 页面路径
	 */
	@RequestMapping(value = "logout")
	public String pageLogout(ModelMap model) {
		logger.debug("进入logout");
		return "support/logout";
	}
}
