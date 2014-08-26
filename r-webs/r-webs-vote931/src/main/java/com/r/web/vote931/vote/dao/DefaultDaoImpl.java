/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.web.vote931.vote.dao;

import org.springframework.stereotype.Repository;

import com.r.web.vote931.support.abs.AbstractDao;
import com.r.web.vote931.support.abs.AbstractDaoImpl;
import com.r.web.vote931.vote.model.AbsVoteItem;

/**
 * @author rain
 */
@Repository("support.dao.default")
public class DefaultDaoImpl extends AbstractDaoImpl<AbsVoteItem> implements AbstractDao<AbsVoteItem> {

    public DefaultDaoImpl() {
        super(AbsVoteItem.class);
    }
}
