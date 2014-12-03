/**
 * 
 */
package com.r.qqcard.card.dao;

import org.springframework.stereotype.Repository;

import com.r.qqcard.card.domain.Compose;
import com.r.qqcard.core.support.AbstractDaoImpl;

/**
 * 合成关系Dao
 * 
 * @author rain
 *
 */
@Repository("dao.compose")
public class ComposeDaoImpl extends AbstractDaoImpl<Compose> implements ComposeDao {

    public ComposeDaoImpl() {
        super(Compose.class);
    }

}
