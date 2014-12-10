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

    /** 根据箱子类型返回此箱子中的所有卡片 */
    Collection<CardBox> queryAll(int cardboxType);

}
