/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.module.vote.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.web.component.incrementer.context.IncrementerContext;
import com.r.web.module.vote.bean.VoteOption;
import com.r.web.module.vote.dao.VoteDao;
import com.r.web.module.vote.model.Vote;
import com.r.web.support.abs.AbstractService;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("vote.service.vote")
public class VoteService extends AbstractService {

    /** 问卷自增长器的类型 */
    public static final String VOTE_INCREMENTER_TYPE = "vote";

    @Resource(name = "incrementer")
    private IncrementerContext incrementer;

    @Resource(name = "vote.dao.vote")
    private VoteDao voteDao;

    @Resource(name = "vote.service.voteitem")
    private VoteItemService voteItemService;

    @Resource(name = "vote.service.votebaseitem")
    private VoteBaseItemService voteBaseItemService;

    /**
     * 查询所有的问卷
     * 
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<Vote> queryAll() {
        return voteDao.queryAll(Order.desc("createDate"));
    }

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
        vote.setNo(incrementer.getIncrementer().nextIntValue(VoteService.VOTE_INCREMENTER_TYPE));
        vote.setTitle(voteOption.getTitle());
        vote.setSubTitle(voteOption.getSubTitle());
        vote.setSignature(voteOption.getSignature());
        vote.setCreateDate(new Date());
        vote.setIsEnable(Boolean.TRUE);
        vote.setVoteItems(voteBaseItemService.queryByRandom(voteOption.getVisize()));
        voteDao.create(vote);
        return vote;
    }
}
