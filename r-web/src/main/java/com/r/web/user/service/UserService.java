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

import com.r.web.user.dao.UserDao;
import com.r.web.user.model.User;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("user.userService")
public class UserService {

	@Resource(name = "user.userDao")
	private UserDao userDao;

	/** 返回所有用户数据 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<User> queryAll() {
		return userDao.queryAll();
	}

}
