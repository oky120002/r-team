package com.r.qqcard.card.qqhome.impl;

import java.util.ArrayList;
import java.util.List;

import com.r.qqcard.card.qqhome.QQHomeBoxStore;
import com.r.qqcard.card.qqhome.QQHomeCardStore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 卡片箱
 * 
 * @author oky
 * 
 */
@XStreamAlias("storebox")
public class QQHomeBoxStoreImpl implements QQHomeBoxStore {
    @XStreamAsAttribute
    private int cur; // 当前拥有卡片
    @XStreamAsAttribute
    private int max; // 最大能装的卡片数量
    @XStreamAsAttribute
    private int nor; // ?

    @XStreamImplicit(itemFieldName = "card")
    private List<QQHomeCardStore> storeCards = new ArrayList<QQHomeCardStore>(); // 卡片箱的卡片

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
     * @return the nor
     */
    public int getNor() {
        return nor;
    }

    /**
     * @param nor
     *            the nor to set
     */
    public void setNor(int nor) {
        this.nor = nor;
    }

    /**
     * @return the storeCards
     */
    public List<QQHomeCardStore> getStoreCards() {
        return storeCards;
    }

    /**
     * @param storeCards
     *            the storeCards to set
     */
    public void setStoreCards(List<QQHomeCardStore> storeCards) {
        this.storeCards = storeCards;
    }

}
