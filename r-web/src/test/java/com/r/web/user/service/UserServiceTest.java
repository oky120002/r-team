/**
 * 
 */
package com.r.web.user.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.web.user.model.User;

/**
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class UserServiceTest extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class); // 日志

	@Resource(name = "user.userService")
	private UserService userService;

	@Resource(name = "passwordEncoder")
	private PasswordEncoder passwordEncoder;

	@Test
	public void testCreateAdminUser() {
		logger.debug("testCreateAdminUser");
		User user = new User();
		user.setId("0");
		user.setCreateDate(new Date());
		user.setEmail("heyu.520@qq.com");
		user.setIsAccountNonLocked(true);
		user.setIsCredentialsNonExpired(true);
		user.setIsEnabled(true);
		user.setIsLock(true);
		user.setNickname("admin");
		user.setPassword("admin");
		user.setUsername("admin");

		userService.createUser(user);
		logger.debug("user : " + user.toString() + "       id : " + user.getId());

		// user.setUsername("admin222");
		userService.us(user);
		User find = userService.find("0");
		logger.debug("user : " + find.toString() + "       id : " + find.getId());

		logger.debug("清空所有用户 : " + String.valueOf(userService.deleteAllUser()) + "位");
	}

	@Test
	public void testCreateUsers() {
		logger.debug("testCreateAdminUser");
		userService.createUser("oky", "oky", "abcd", "aaaa", true, false);
		userService.createUser("oky2", "oky", "abcd", "aaaa", true, false);
		userService.createUser("oky3", "oky", "abcd", "aaaa", true, false);
		userService.createUser("oky4", "oky", "abcd", "aaaa", true, false);
		userService.createUser("oky5", "oky", "abcd", "aaaa", true, false);

		List<User> queryAll = userService.queryAll();
		for (User user : queryAll) {
			logger.debug(user.toString() + " : " + passwordEncoder.matches("oky", user.getPassword()) + " : " + user.getId());
		}

		logger.debug("清空所有用户 : " + String.valueOf(userService.deleteAllUser()) + "位");
	}
}
