/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.module.vote.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.web.component.incrementer.context.IncrementerContext;
import com.r.web.module.vote.dao.VoteItemDao;
import com.r.web.module.vote.model.VoteItem;
import com.r.web.support.abs.AbstractService;

/**
 * 问卷项Service<br/>
 * 
 * @author rain
 */
@Service("vote.service.voteitem")
public class VoteItemService extends AbstractService {

    /** 问卷项自增长器类型 */
    public static final String VOTEITEM_INCREMENTER_TYPE = "vote_item";

    @Resource(name = "incrementer")
    private IncrementerContext incrementer;

    @Resource(name = "vote.dao.voteitem")
    private VoteItemDao voteItemDao;

    /**
     * 创建问卷项<br/>
     * 
     * @param voteItem
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void create(VoteItem voteItem) {
        voteItemDao.create(voteItem);
    }

    /**
     * 创建问卷项集合<br/>
     * 
     * @param voteItems
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void creates(Collection<VoteItem> voteItems) {
        voteItemDao.creates(voteItems);
    }

    /**
     * 更新问卷项
     * 
     * @param vote
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void update(VoteItem vote) {
        voteItemDao.update(vote);
    }

}
