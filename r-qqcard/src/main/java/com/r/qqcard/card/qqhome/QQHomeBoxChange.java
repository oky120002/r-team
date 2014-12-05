package com.r.qqcard.card.qqhome;

import java.util.Collection;

/**
 * 换卡箱的卡片
 * 
 * @author oky
 * 
 */
public interface QQHomeBoxChange {

    /** 获得当前拥有数量 */
    int getCur();

    /** 获得vip数量? */
    int getVipnum();

    /** 获取最大数量 */
    int getMax();

    /** 获取? */
    String getExch();

    /** 获取换卡箱的卡片 */
    Collection<QQHomeCardChange> getChangeCards();
}