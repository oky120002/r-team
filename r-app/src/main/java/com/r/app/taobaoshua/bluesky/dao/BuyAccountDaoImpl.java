/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.dao;

import org.springframework.stereotype.Repository;

import com.r.app.core.support.AbstractDaoImpl;
import com.r.app.taobaoshua.bluesky.model.BuyAccount;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("buyAccountDao")
public class BuyAccountDaoImpl extends AbstractDaoImpl<BuyAccount> implements BuyAccountDao {
	private static final Logger logger = LoggerFactory.getLogger(BuyAccountDaoImpl.class); // 日志

	public BuyAccountDaoImpl() {
		super(BuyAccount.class);
		logger.info("BuyAccountDaoImpl Instance............................");
	}

}
