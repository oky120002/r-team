package com.r.web.arean.account.dao;

import org.springframework.stereotype.Repository;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.web.arean.account.model.User;
import com.r.web.arean.core.abs.AbstractDaoImpl;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("account.userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class); // 日志

	public UserDaoImpl() {
		super(User.class);
		logger.info("Instance UserDaoImpl............................");
	}
}
