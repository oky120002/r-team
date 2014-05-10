/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.web.authority.dao.AutUserDetailsDao;
import com.r.web.user.dao.UserDao;
import com.r.web.user.model.User;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("user.userService")
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class); // 日志

	@Resource(name = "authority.autUserDetailsDao")
	private AutUserDetailsDao autUserDetailsDao;

	@Resource(name = "user.userDao")
	private UserDao userDao;

	/**
	 * 返回所有用户实体
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<User> queryAll() {
		return userDao.queryAll();
	}

	/**
	 * 创建用户实体
	 * 
	 * @param username
	 *            账号
	 * @param password
	 *            密码
	 * @param email
	 *            电子邮件
	 * @param nickname
	 *            昵称
	 * @param isEnabled
	 *            是否启用
	 * @param isLock
	 *            是否锁定
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public User createUser(String username, String password, String email, String nickname, Boolean isEnabled, Boolean isLock) {
		logger.debug("创建用户({},{},{})", username, nickname, email);
		User user = new User(username, password, email, nickname, isEnabled, isLock);
		userDao.create(user);
		return user;
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
	}
}
