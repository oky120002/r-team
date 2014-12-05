package com.r.qqcard.card.qqhome.impl;

import java.util.ArrayList;
import java.util.List;

import com.r.qqcard.card.qqhome.QQHomeBoxStove;
import com.r.qqcard.card.qqhome.QQHomeCardStove;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 炼卡箱
 * 
 * @author oky
 * 
 */
@XStreamAlias("stovebox")
public class QQHomeBoxStoveImpl implements QQHomeBoxStove {
    @XStreamAsAttribute
    private int cur; // 当前数量
    @XStreamAsAttribute
    private int max; // 最大数量
    @XStreamAsAttribute
    private int stovenum; // 炼卡炉子数量

    @XStreamImplicit(itemFieldName = "card")
    private List<QQHomeCardStove> stoveCards = new ArrayList<QQHomeCardStove>(); // 换卡箱的卡片

    /**
     * @return the cur
     */
    public int getCur() {
        return cur;
    }

    /**
     * @param cur
     *            the cur to set
     */
    public void setCur(int cur) {
        this.cur = cur;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max
     *            the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the stovenum
     */
    public int getStovenum() {
        return stovenum;
    }

    /**
     * @param stovenum
     *            the stovenum to set
     */
    public void setStovenum(int stovenum) {
        this.stovenum = stovenum;
    }

    /**
     * @return the stoveCards
     */
    public List<QQHomeCardStove> getStoveCards() {
        return stoveCards;
    }

    /**
     * @param stoveCards
     *            the stoveCards to set
     */
    public void setStoveCards(List<QQHomeCardStove> stoveCards) {
        this.stoveCards = stoveCards;
    }

}
