/**
 * 
 */
package com.r.qqcard.card.dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.r.qqcard.card.model.CardBox;
import com.r.qqcard.core.support.AbstractDaoImpl;

/**
 * 卡箱dao
 * 
 * @author rain
 *
 */
@Repository("dao.cardbox")
public class CardBoxDaoImpl extends AbstractDaoImpl<CardBox> implements CardBoxDao {

    public CardBoxDaoImpl() {
        super(CardBox.class);
    }

    @Override
    public Collection<CardBox> queryAll(int cardboxType) {
        CardBox cardBox = new CardBox();
        cardBox.setCardBoxType(Integer.valueOf(cardboxType));
        cardBox.setStatus(Integer.valueOf(0));
        return queryByExample(cardBox);
    }

    @Override
    public Collection<CardBox> queryAll() {
        CardBox cardBox = new CardBox();
        cardBox.setStatus(Integer.valueOf(0));
        return queryByExample(cardBox);
    }

    @Override
    public Collection<CardBox> queryTidyCards() {
        StringBuffer hql = new StringBuffer();

        hql.append(" from ").append(CardBox.class.getName()).append(" card ");
        hql.append(" where ").append(' ');
        hql.append(" ( card.cardBoxType = 0 and 10 < card.gold ) ");
        hql.append(" or ( card.cardBoxType = 1 and 10 = card.gold ) ");

        return queryByHql(hql);
    }
}
