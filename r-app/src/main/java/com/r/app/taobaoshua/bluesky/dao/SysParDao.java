/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.dao;

import com.r.app.taobaoshua.bluesky.core.AbstractDao;
import com.r.app.taobaoshua.bluesky.model.SysPar;
import com.r.app.taobaoshua.bluesky.model.enumsyspar.SysParName;

/**
 * 用户Dao
 * 
 * @author rain
 */
public interface SysParDao extends AbstractDao<SysPar> {
	/** 根据name查询参数 */
	SysPar findByName(SysParName name);
}
