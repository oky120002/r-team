/**
 * 
 */
package com.r.qqcard.card.qqhome.other;

import java.util.ArrayList;
import java.util.List;

import com.r.qqcard.card.qqhome.QQHomeCardChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 购卡返回xml实体
 * 
 * @author rain
 * 
 */
@XStreamAlias(value = "QQHOME")
public class QQBuyCard {
    @XStreamAsAttribute
    private int code; // ?

    @XStreamImplicit(itemFieldName = "card")
    private List<QQHomeCardChange> cards = new ArrayList<QQHomeCardChange>(); // 换卡箱的卡片

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the cards
     */
    public List<QQHomeCardChange> getCards() {
        return cards;
    }

    /**
     * @param cards
     *            the cards to set
     */
    public void setCards(List<QQHomeCardChange> cards) {
        this.cards = cards;
    }

}
