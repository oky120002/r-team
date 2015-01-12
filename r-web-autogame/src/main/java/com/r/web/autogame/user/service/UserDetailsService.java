/**
 * 
 */
package com.r.web.autogame.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * spring security 获取用户实体
 * 
 * @author rain
 * 
 */
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}