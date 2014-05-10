/**
 * 
 */
package com.r.web.user.service;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.web.user.model.User;

/**
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class UserServiceTest extends TestCase {

	@Resource(name = "user.userService")
	private UserService userService;

	@Test
	public void test() {
		User user1 = userService.createUser("oky", "oky", "abcd", "aaaa", true, false);
		User user2 = userService.createUser("oky2", "oky2", "abcd", "aaaa", true, false);
		User user3 = userService.createUser("oky3", "oky3", "abcd", "aaaa", true, false);
		User user4 = userService.createUser("oky4", "oky4", "abcd", "aaaa", true, false);
		User user5 = userService.createUser("oky5", "oky5", "abcd", "aaaa", true, false);

		List<User> queryAll = userService.queryAll();
		for (User user : queryAll) {
			System.out.println(user.toString());
		}

		userService.deleteUser(user1);
		userService.deleteUser(user2);
		userService.deleteUser(user3);
		userService.deleteUser(user4);
		userService.deleteUser(user5);
		
		System.out.println(userService.queryAll().size());
	}
}
