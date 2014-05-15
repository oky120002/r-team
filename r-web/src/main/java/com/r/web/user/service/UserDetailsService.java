/**
 * 
 */
package com.r.web.user.service;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.web.user.dao.UserDetailsDao;

/**
 * spring security 获取用户实体
 * 
 * @author rain
 * 
 */
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class); // 日志

	@Resource(name = "user.autUserDetailsDao")
	private UserDetailsDao userDetailsDao;

	public UserDetailsService() {
		super();
		logger.info("Instance AutUserDetailsService............................");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Bad credentials
		try {
			UserDetails user = userDetailsDao.findByUsername(username);
			logger.info("-----获取登陆用户:[" + user + "]");
			if (user == null) {
				throw new UsernameNotFoundException("无此用户!");
			}
			return user;
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.toString());
		}
	}

}