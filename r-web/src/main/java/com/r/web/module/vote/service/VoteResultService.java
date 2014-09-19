/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.module.vote.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.web.component.incrementer.context.IncrementerContext;
import com.r.web.module.vote.dao.VoteResultDao;
import com.r.web.module.vote.model.VoteResult;
import com.r.web.support.abs.AbstractService;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("vote.service.voteresult")
public class VoteResultService extends AbstractService {

    /** 问卷结果自增长器类型 */
    private static final String VOTE_RESULT_INCREMENTER_TYPE = "vote_result";

    @Resource(name = "incrementer")
    private IncrementerContext incrementer;

    @Resource(name = "vote.dao.voteresult")
    private VoteResultDao voteResultDao;

    /**
     * 根据问卷结果实体ID查询问卷结果
     * 
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public VoteResult find(String id) {
        return voteResultDao.find(id);
    }

    /**
     * 创建问卷结果<br/>
     * 如果没有设置编号,则自动设置
     * 
     * @param result
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void create(VoteResult result) {
        if (result.getNo() == null) {
            result.setNo(incrementer.getIncrementer().nextIntValue(VoteResultService.VOTE_RESULT_INCREMENTER_TYPE));
        }
        voteResultDao.create(result);
    }

}
