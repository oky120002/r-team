package com.r.qqcard.card.qqhome.impl;

import java.util.ArrayList;
import java.util.List;

import com.r.qqcard.card.qqhome.QQHomeBoxChange;
import com.r.qqcard.card.qqhome.QQHomeCardChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 交换箱
 * 
 * @author oky
 * 
 */
@XStreamAlias("changebox")
public class QQHomeBoxChangeImpl implements QQHomeBoxChange {
    @XStreamAsAttribute
    private int cur; // 当前拥有数量
    @XStreamAsAttribute
    private int vipnum; // vip数量?
    @XStreamAsAttribute
    private int max; // 最大数量
    @XStreamAsAttribute
    private String exch; // ?

    @XStreamImplicit(itemFieldName = "card")
    private List<QQHomeCardChange> changeCards = new ArrayList<QQHomeCardChange>(); // 换卡箱的卡片

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
     * @return the vipnum
     */
    public int getVipnum() {
        return vipnum;
    }

    /**
     * @param vipnum
     *            the vipnum to set
     */
    public void setVipnum(int vipnum) {
        this.vipnum = vipnum;
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
     * @return the exch
     */
    public String getExch() {
        return exch;
    }

    /**
     * @param exch
     *            the exch to set
     */
    public void setExch(String exch) {
        this.exch = exch;
    }

    /**
     * @return the changeCards
     */
    public List<QQHomeCardChange> getChangeCards() {
        return changeCards;
    }

    /**
     * @param changeCards
     *            the changeCards to set
     */
    public void setChangeCards(List<QQHomeCardChange> changeCards) {
        this.changeCards = changeCards;
    }

}
