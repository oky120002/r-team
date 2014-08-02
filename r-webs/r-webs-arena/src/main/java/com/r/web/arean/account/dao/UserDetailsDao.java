package com.r.web.arean.account.dao;

import org.springframework.security.core.userdetails.UserDetails;

import com.r.web.arean.account.model.User;
import com.r.web.arean.core.abs.AbstractDao;

/**
 * 用户Dao
 * 
 * @author rain
 */
public interface UserDetailsDao extends AbstractDao<User> {

	/**
	 * 
	 * 根据用户名查找用户<br />
	 * 只能查找用户名唯一的用户，如果找到多个用户，则抛出错误
	 * 
	 * @param username
	 *            用户名
	 * 
	 * @return User 用户
	 * @author rain
	 */
	UserDetails findByUsername(String username);
}
