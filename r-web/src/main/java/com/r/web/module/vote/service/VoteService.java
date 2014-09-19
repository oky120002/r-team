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
import com.r.web.module.vote.bean.VoteResultOption;
import com.r.web.module.vote.dao.VoteDao;
import com.r.web.module.vote.model.Vote;
import com.r.web.module.vote.model.VoteItem;
import com.r.web.module.vote.model.VoteResult;
import com.r.web.module.vote.model.VoteResultItem;
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

    @Resource(name = "vote.service.voteresult")
    private VoteResultService voteResultService;

    @Resource(name = "vote.service.voteresultitem")
    private VoteResultItemService voteResultItemService;

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
     * 根据问卷选项生成问卷<br/>
     * 如果没有设置编号,则自动设置
     * 
     * @param voteOption
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Vote createVote(VoteOption voteOption) {
        // 生成问卷项
        List<VoteItem> voteItems = voteBaseItemService.queryByRandom(voteOption.getVisize());
        voteItemService.creates(voteItems);

        // 生成问卷
        Vote vote = new Vote();
        vote.setNo(incrementer.getIncrementer().nextIntValue(VoteService.VOTE_INCREMENTER_TYPE));
        vote.setTitle(voteOption.getTitle());
        vote.setSubTitle(voteOption.getSubTitle());
        vote.setSignature(voteOption.getSignature());
        vote.setCreateDate(new Date());
        vote.setIsEnable(Boolean.TRUE);
        vote.setVoteItems(voteItems);
        voteDao.create(vote);
        return vote;
    }

    /**
     * 答题
     * 
     * @param voteResultOption
     * @return
     */
    public VoteResult answerVote(VoteResultOption voteResultOption) {
        // 生成问卷结果项
        List<VoteResultItem> voteResultItems = voteResultOption.getVoteResultItems(voteBaseItemService);
        voteResultItemService.creates(voteResultItems);

        // 生成问卷结果
        VoteResult result = new VoteResult();
        result.setVote(voteDao.find(voteResultOption.getVoteId()));
        result.setRemark(voteResultOption.getRemark());
        result.setVoteResultItems(voteResultItems);
        voteResultService.create(result);
        return result;
    }
}
