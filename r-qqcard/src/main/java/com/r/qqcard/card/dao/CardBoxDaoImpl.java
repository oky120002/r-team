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
        cardBox.setBoxtype(cardboxType);
        return queryByExample(cardBox);
    }

}
