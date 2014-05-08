/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.authority.dao;

import com.r.web.authority.model.AutUserDetails;
import com.r.web.support.AbstractDao;

/**
 * 用户Dao
 * 
 * @author rain
 */
public interface AutUserDetailsDao extends AbstractDao<AutUserDetails> {

	/**
	 * 
	 * 根据用户名查找用户<br />
	 * 只能查找用户名唯一的用户，如果找到多个用户，则抛出错误
	 * 
	 * @param username
	 *            用户名
	 * 
	 * @return User 用户
	 * @author rain
	 */
	AutUserDetails findByUsername(String username);
}
