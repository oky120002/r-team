/**
 * 
 */
package com.r.qqcard.card.dao;

import java.util.Collection;

import com.r.qqcard.card.model.CardBox;
import com.r.qqcard.core.support.AbstractDao;

/**
 * 卡箱Dao
 * 
 * @author rain
 *
 */
public interface CardBoxDao extends AbstractDao<CardBox> {

    /** 根据箱子类型返回此箱子中的所有卡片(0:换卡箱,1:保险箱) */
    Collection<CardBox> queryAll(int cardboxType);

    /** 返回所有箱子卡片 */
    Collection<CardBox> queryAll();

    /** 返回需要整理的卡片(换卡箱中金币大于10的卡片,保险箱中金币等于10的卡片) */
    Collection<CardBox> queryTidyCards();

}
