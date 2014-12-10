/**
 * 
 */
package com.r.qqcard.card.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.r.core.exceptions.data.DataErrorException;
import com.r.qqcard.card.model.Card;
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

    @Override
    public Card findCardByCardId(int cardid) {
        Card card = new Card();
        card.setCardid(cardid);
        List<Card> cards = queryByExample(card);
        if (CollectionUtils.isEmpty(cards)) {
            return null;
        } else if (cards.size() > 1) {
            throw new DataErrorException("卡片信息错误,出现重复卡片id的卡片");
        }
        return queryByExample(card).get(0);
    }
}
