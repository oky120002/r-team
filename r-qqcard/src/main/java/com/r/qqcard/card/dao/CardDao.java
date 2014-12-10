/**
 * 
 */
package com.r.qqcard.card.dao;

import com.r.qqcard.card.model.Card;
import com.r.qqcard.core.support.AbstractDao;

/**
 * 卡片Dao
 * 
 * @author rain
 *
 */
public interface CardDao extends AbstractDao<Card> {
    /** 根据卡片id查询卡片 */
    public Card findCardByCardId(int cardid);
}
