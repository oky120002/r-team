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

    @Resource(name = "vote.dao.voteitem")
    private VoteItemDao voteItemDao;

    /**
     * 创建问卷项
     * @param vi
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void create(VoteItem vi) {
        voteItemDao.create(vi);
    }

}
