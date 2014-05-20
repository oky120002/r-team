/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.user.dao;

import org.springframework.stereotype.Repository;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.web.core.hibernate.AbstractDaoImpl;
import com.r.web.user.model.User;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("user.userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class); // 日志

	public UserDaoImpl() {
		super(User.class);
		logger.info("Instance UserDaoImpl............................");
	}
}
