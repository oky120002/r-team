/**
 * 
 */
package com.r.qqcard.card.dao;

import org.springframework.stereotype.Repository;

import com.r.qqcard.card.domain.Card;
import com.r.qqcard.core.support.AbstractDaoImpl;

/**
 * 卡片dao
 * 
 * @author rain
 *
 */
@Repository("dao.card")
public class CardDaoImpl extends AbstractDaoImpl<Card> implements CardDao {

    public CardDaoImpl() {
        super(Card.class);
    }

}
