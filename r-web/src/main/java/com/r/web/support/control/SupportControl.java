/**
 * 
 */
package com.r.web.support.control;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.web.support.service.InitSystemService;

/**
 * 支持视图控制器
 * 
 * @author oky
 * 
 */
@Controller("supportControl")
@RequestMapping(value = "/")
public class SupportControl {

	private static final Logger logger = LoggerFactory.getLogger(SupportControl.class);

	@Resource(name = "suport.initSystemService")
	private InitSystemService initSystemService;

	public SupportControl() {
		logger.info("Instance SupportControl............................");
	}

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
	@RequestMapping(value = "main")
	public String mainLogin(ModelMap model) {
		logger.debug("进入main");
		return "support/main";
	}

	// -------系统初始化-------//

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
	 * 初始化后台管理员数据
	 * 
	 * @param adminUsername
	 *            后台管理员账号
	 * @param password
	 *            后台管理员密码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "init/complete")
	public String pageInitComplete(@RequestParam("username") String username, @RequestParam("password") String password, ModelMap model) {
		initSystemService.initSystem(username, password);
		return "support/initcompleted";
	}

	// -------系统初始化end-------//

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
