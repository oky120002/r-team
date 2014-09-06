/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.module.vote.dao;

import org.springframework.stereotype.Repository;

import com.r.web.module.vote.model.Vote;
import com.r.web.support.abs.AbstractDaoImpl;

/**
 * @author rain
 */
@Repository("vote.doa.vote")
public class VoteDaoImpl extends AbstractDaoImpl<Vote> implements VoteDao {

	public VoteDaoImpl() {
		super(Vote.class);
	}

}
