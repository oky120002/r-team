/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.user.dao;

import org.springframework.stereotype.Repository;

import com.r.web.support.AbstractDaoImpl;
import com.r.web.user.model.User;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("user.userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}
}
