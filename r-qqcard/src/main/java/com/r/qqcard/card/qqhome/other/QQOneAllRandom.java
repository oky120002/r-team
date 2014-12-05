/**
 * 
 */
package com.r.qqcard.card.qqhome.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.r.qqcard.card.qqhome.QQHomeCardChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 一键抽卡返回xml实体
 * 
 * @author oky
 * 
 */
@XStreamAlias(value = "QQHOME")
public class QQOneAllRandom {
    @XStreamAsAttribute
    private int code; // ?
    @XStreamAsAttribute
    private int lastrandti; // ?
    @XStreamAsAttribute
    private int num; // 一键抽卡数量
    @XStreamAsAttribute
    private int remain; // ?

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
     * @return the lastrandti
     */
    public int getLastrandti() {
        return lastrandti;
    }

    /**
     * @param lastrandti
     *            the lastrandti to set
     */
    public void setLastrandti(int lastrandti) {
        this.lastrandti = lastrandti;
    }

    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num
     *            the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * @return the remain
     */
    public int getRemain() {
        return remain;
    }

    /**
     * @param remain
     *            the remain to set
     */
    public void setRemain(int remain) {
        this.remain = remain;
    }

    /**
     * @return the cards
     */
    public Collection<QQHomeCardChange> getCards() {
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
