package com.r.qqcard.card.qqhome;

import java.util.Collection;

/**
 * 炼卡箱
 * 
 * @author oky
 * 
 */
public interface QQHomeBoxStove {

    /** 获得当前数量 */
    int getCur();

    /** 获得最大数量 */
    int getMax();

    /** 获得炼卡炉子数量 */
    int getStovenum();

    /** 获得炼卡箱卡片 */
    Collection<QQHomeCardStove> getStoveCards();
}