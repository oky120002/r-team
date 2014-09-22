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
import com.r.web.module.vote.dao.VoteResultItemDao;
import com.r.web.module.vote.model.VoteResultItem;
import com.r.web.support.abs.AbstractService;

/**
 * 用户Service<br/>
 * 
 * @author rain
 */
@Service("vote.service.voteresultitem")
public class VoteResultItemService extends AbstractService {

    /** 问卷结果自增长器类型 */
//    private static final String VOTE_RESULT_ITEM_INCREMENTER_TYPE = "vote_result_item";

    @Resource(name = "incrementer")
    private IncrementerContext incrementer;

    @Resource(name = "vote.dao.voteresultitem")
    private VoteResultItemDao voteResulItemtDao;

    /**
     * 创建问卷结果<br/>
     * 
     * @param result
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void creates(Collection<VoteResultItem> resultitems) {
        voteResulItemtDao.creates(resultitems);
    }
}
