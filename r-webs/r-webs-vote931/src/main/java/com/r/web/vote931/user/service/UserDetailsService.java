/**
 * 
 */
package com.r.web.vote931.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.web.vote931.support.abs.AbstractService;

/**
 * spring security 获取用户实体
 * 
 * @author rain
 * 
 */
public class UserDetailsService extends AbstractService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
}