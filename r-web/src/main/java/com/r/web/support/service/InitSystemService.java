/**
 * 
 */
package com.r.web.support.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.web.user.service.UserService;

/**
 * @author Administrator
 *
 */
@Service("suport.initSystemService")
public class InitSystemService {
	private static final Logger logger = LoggerFactory.getLogger(InitSystemService.class); // 日志

	@Resource(name = "user.userService")
	private UserService userService;

	/**
	 * 初始化系统管理员账号
	 * 
	 * @param adminUsername
	 *            管理员账号
	 * @param password
	 *            管理员密码
	 */
	public void initSystem(String adminUsername, String password) {
		logger.debug("初始化管理员帐号({} , {})", adminUsername);

	}
}
