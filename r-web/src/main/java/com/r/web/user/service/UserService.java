/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.user.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;
import com.r.core.exceptions.arg.ArgNullException;
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

	public UserService() {
		super();
		logger.info("Instance UserService............................");
	}

	@Resource(name = "user.userDao")
	private UserDao userDao; // 用户Dao

	@Resource(name = "passwordEncoder")
	private PasswordEncoder passwordEncoder; // 密码

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
	 * 创建用户
	 * 
	 * @param user
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void createUser(User user) {
		userDao.create(user);
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
	public User createUser(String username, String password, String email, String nickname, boolean isEnabled, boolean isLock) {
		if (StringUtils.isBlank(username)) {
			throw new ArgNullException("创建用户时\"username\"不能为空!");
		}
		if (StringUtils.isBlank(password)) {
			throw new ArgNullException("创建用户时\"password\"不能为空!");
		}
		if (StringUtils.isBlank(email)) {
			throw new ArgNullException("创建用户时\"email\"不能为空!");
		}
		if (StringUtils.isBlank(nickname)) {
			throw new ArgNullException("创建用户时\"nickname\"不能为空!");
		}

		logger.debug("创建用户({},{},{})", username, nickname, email);
		password = passwordEncoder.encode(password);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setNickname(nickname);
		user.setIsEnabled(Boolean.valueOf(isEnabled));
		user.setIsLock(Boolean.valueOf(isLock));
		user.setCreateDate(new Date());
		user.setLoginTimes(Integer.valueOf(0));
		user.setIsAccountNonLocked(Boolean.TRUE);
		user.setIsCredentialsNonExpired(Boolean.TRUE);
		userDao.create(user);
		return user;
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void updateUser(User user) {
		userDao.update(user);
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

	/**
	 * 删除所有用户
	 * 
	 * @return integer 删除实体个数
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public int deleteAllUser() {
		return userDao.deleteAll();
	}
}
