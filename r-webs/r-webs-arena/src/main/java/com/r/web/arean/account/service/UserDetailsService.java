/**
 * 
 */
package com.r.web.arean.account.service;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.web.arean.account.dao.UserDao;

/**
 * spring security 获取用户实体
 * 
 * @author rain
 * 
 */
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class); // 日志

    public UserDetailsService() {
        super();
        logger.info("Instance AutUserDetailsService............................");
    }

    @Resource(name = "account.userDao")
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }
}