/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.vote931.vote.dao;

import org.springframework.stereotype.Repository;

import com.r.web.vote931.support.abs.AbstractDaoImpl;
import com.r.web.vote931.vote.model.CompletionVoteItem;

/**
 * @author rain
 */
@Repository("vote.dao.completionVoteItem")
public class CompletionVoteItemDaoImpl extends AbstractDaoImpl<CompletionVoteItem> implements CompletionVoteItemDao {

    public CompletionVoteItemDaoImpl() {
        super(CompletionVoteItem.class);
    }
}
