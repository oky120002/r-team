/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.r.app.core.support.AbstractDaoImpl;
import com.r.app.taobaoshua.bluesky.model.SysPar;
import com.r.app.taobaoshua.bluesky.model.enumsyspar.SysParName;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("syaparDao")
public class SysParDaoImpl extends AbstractDaoImpl<SysPar> implements SysParDao {
	private static final Logger logger = LoggerFactory.getLogger(SysParDaoImpl.class); // 日志

	public SysParDaoImpl() {
		super(SysPar.class);
		logger.info("SysParDaoImpl Instance............................");
	}

	@Override
	public SysPar findByName(SysParName name) {
		SysPar sysPar = new SysPar();
		sysPar.setName(name);

		List<SysPar> sysPars = queryByExample(sysPar);
		if (CollectionUtils.isNotEmpty(sysPars)) {
			return sysPars.get(0);
		}
		return null;
	}

}
