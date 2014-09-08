/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.module.vote.dao;

import org.springframework.stereotype.Repository;

import com.r.web.module.vote.model.VoteItem;
import com.r.web.support.abs.AbstractDaoImpl;

/**
 * 问卷项Dao
 * 
 * @author rain
 */
@Repository("vote.dao.voteitem")
public class VoteItemDaoImpl extends AbstractDaoImpl<VoteItem> implements VoteItemDao {

    public VoteItemDaoImpl() {
        super(VoteItem.class);
    }

}
