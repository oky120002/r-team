/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.module.vote.dao;

import org.springframework.stereotype.Repository;

import com.r.web.module.vote.model.base.VoteBaseItemImpl;
import com.r.web.support.abs.AbstractDaoImpl;

/**
 * 基础问卷项Dao
 * 
 * @author rain
 */
@Repository("vote.dao.votebaseitem")
public class VoteBaseItemDaoImpl  extends AbstractDaoImpl<VoteBaseItemImpl> implements VoteBaseItemDao {

    public VoteBaseItemDaoImpl() {
        super(VoteBaseItemImpl.class);
    }
}
