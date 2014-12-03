/**
 * 
 */
package com.r.qqcard.card.dao;

import org.springframework.stereotype.Repository;

import com.r.qqcard.card.domain.Gift;
import com.r.qqcard.core.support.AbstractDaoImpl;

/**
 * QQ秀Dao
 * 
 * @author rain
 *
 */
@Repository("dao.gift")
public class GiftDaoImpl extends AbstractDaoImpl<Gift> implements GiftDao {

    public GiftDaoImpl() {
        super(Gift.class);
    }

}
