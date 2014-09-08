/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.module.vote.discard;

import org.springframework.stereotype.Repository;

import com.r.web.module.vote.model.base.CompletionVoteBaseItem;
import com.r.web.support.abs.AbstractDaoImpl;

/**
 * @author rain
 */
@Repository("vote.dao.completionVoteItem")
public class CompletionVoteItemDaoImpl extends AbstractDaoImpl<CompletionVoteBaseItem> implements CompletionVoteItemDao {

    public CompletionVoteItemDaoImpl() {
        super(CompletionVoteBaseItem.class);
    }
}
