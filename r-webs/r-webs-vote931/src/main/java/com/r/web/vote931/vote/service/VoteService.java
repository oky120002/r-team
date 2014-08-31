/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.vote931.vote.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.web.vote931.support.abs.AbstractService;
import com.r.web.vote931.vote.bean.VoteOption;
import com.r.web.vote931.vote.dao.VoteDao;
import com.r.web.vote931.vote.model.Vote;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("vote.service.vote")
public class VoteService extends AbstractService {

	@Resource(name = "vote.doa.vote")
	private VoteDao voteDao;

	@Resource(name = "vote.service.voteitem")
	private VoteItemService voteItemService;

	/** 根据id查询问卷 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public Vote find(String id) {
		return voteDao.find(id);
	}

	/**
	 * 根据问卷选项生成问卷
	 * 
	 * @param voteOption
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public Vote createVote(VoteOption voteOption) {
		Vote vote = new Vote();
		vote.setTitle(voteOption.getTitle());
		vote.setSignature(voteOption.getSignature());
		vote.setVoteItems(voteItemService.queryByRandom(voteOption.getVisize()));
		vote.setCreateDate(new Date());
		voteDao.create(vote);
		return vote;
	}
}
