/**
 * 
 */
package com.r.web.module.vote.dao;

import org.springframework.stereotype.Repository;

import com.r.web.module.vote.model.VoteResultItem;
import com.r.web.support.abs.AbstractDaoImpl;

/**
 * @author rain
 *
 */
@Repository("vote.dao.voteresultitem")
public class VoteResultItemDaoImpl extends AbstractDaoImpl<VoteResultItem> implements VoteResultItemDao {

    public VoteResultItemDaoImpl() {
        super(VoteResultItem.class);
    }

}
