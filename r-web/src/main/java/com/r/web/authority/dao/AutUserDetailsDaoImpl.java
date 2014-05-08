/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.authority.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.web.authority.model.AutUserDetails;
import com.r.web.support.AbstractDaoImpl;
import com.r.web.support.KeyValue;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("authority.autUserDetailsDao")
public class AutUserDetailsDaoImpl extends AbstractDaoImpl<AutUserDetails> implements AutUserDetailsDao {

	public AutUserDetailsDaoImpl() {
		super(AutUserDetails.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public AutUserDetails findByUsername(String username) {
		List<AutUserDetails> users = queryByHql(" from " + AutUserDetails.class.getName() + " where username = :username", KeyValue.kv("username", username));
		if (CollectionUtils.isEmpty(users)) {
			return null;
		}
		if (users.size() > 1) {
			throw new UsernameNotFoundException("存在两个[" + username + "]用户；登陆失败！");
		}

		return users.get(0);
	}
}
