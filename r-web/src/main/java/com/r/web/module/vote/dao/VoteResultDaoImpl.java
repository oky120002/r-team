/**
 * 
 */
package com.r.web.module.vote.dao;

import org.springframework.stereotype.Repository;

import com.r.web.module.vote.model.VoteResult;
import com.r.web.support.abs.AbstractDaoImpl;

/**
 * @author rain
 *
 */
@Repository("vote.dao.voteresult")
public class VoteResultDaoImpl extends AbstractDaoImpl<VoteResult> implements VoteResultDao {

    public VoteResultDaoImpl() {
        super(VoteResult.class);
    }

}
