/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.module.vote.dao;

import org.springframework.stereotype.Repository;

import com.r.web.module.vote.model.CompletionVoteItem;
import com.r.web.module.vote.model.YesOrNoVoteItem;
import com.r.web.support.abs.AbstractDaoImpl;

/**
 * @author rain
 */
@Repository("vote.dao.yesOrNoVoteItem")
public class YesOrNoVoteItemDaoImpl extends AbstractDaoImpl<YesOrNoVoteItem> implements YesOrNoVoteItemDao {

    public YesOrNoVoteItemDaoImpl() {
        super(CompletionVoteItem.class);
    }
    
    
}
