package com.r.web.arean.account.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.web.arean.account.model.User;
import com.r.web.arean.core.abs.AbstractDaoImpl;
import com.r.web.arean.support.KV;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("account.userDetailsDao")
public class UserDetailsDaoImpl extends AbstractDaoImpl<User> implements UserDetailsDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsDaoImpl.class);

	public UserDetailsDaoImpl() {
		super(User.class);
		logger.info("Instance AutUserDetailsDaoImpl............................");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public UserDetails findByUsername(String username) {
		List<User> users = queryByHql(" from " + User.class.getName() + " where username = :username", KV.kv("username", username));
		if (CollectionUtils.isEmpty(users)) {
			return null;
		}
		if (users.size() > 1) {
			throw new UsernameNotFoundException("存在两个[" + username + "]用户；登陆失败！");
		}
		return users.get(0);
	}
}
