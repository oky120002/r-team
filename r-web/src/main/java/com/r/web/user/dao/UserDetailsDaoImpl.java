/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.user.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.web.core.hibernate.AbstractDaoImpl;
import com.r.web.core.hibernate.KeyValue;
import com.r.web.user.model.User;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("user.autUserDetailsDao")
public class UserDetailsDaoImpl extends AbstractDaoImpl<User> implements UserDetailsDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsDaoImpl.class);

	public UserDetailsDaoImpl() {
		super(User.class);
		logger.info("Instance AutUserDetailsDaoImpl............................");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public UserDetails findByUsername(String username) {
		List<User> users = queryByHql(" from " + User.class.getName() + " where username = :username", KeyValue.kv("username", username));
		if (CollectionUtils.isEmpty(users)) {
			return null;
		}
		if (users.size() > 1) {
			throw new UsernameNotFoundException("存在两个[" + username + "]用户；登陆失败！");
		}
		return users.get(0);
	}
}
