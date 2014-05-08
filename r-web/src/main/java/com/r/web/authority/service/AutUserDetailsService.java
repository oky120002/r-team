/**
 * 
 */
package com.r.web.authority.service;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.web.authority.dao.AutUserDetailsDao;

/**
 * spring security 获取用户实体
 * 
 * @author rain
 * 
 */
public class AutUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(AutUserDetailsService.class); // 日志

	@Resource(name = "authority.autUserDetailsDao")
	private AutUserDetailsDao autUserDetailsDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Bad credentials
		try {
			UserDetails user = autUserDetailsDao.findByUsername(username);
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