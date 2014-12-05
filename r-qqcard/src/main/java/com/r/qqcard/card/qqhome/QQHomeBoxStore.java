package com.r.qqcard.card.qqhome;

import java.util.Collection;

/**
 * 卡片箱
 * 
 * @author oky
 * 
 */
public interface QQHomeBoxStore {

    /** 获得当前数量 */
    int getCur();

    /** 获得最大数量 */
    int getMax();

    /** 获得? */
    int getNor();

    /** 获得卡片箱中的卡片 */
    Collection<QQHomeCardStore> getStoreCards();
}