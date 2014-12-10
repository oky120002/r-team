/**
 * 
 */
package com.r.qqcard.card.dao;

import org.springframework.stereotype.Repository;

import com.r.qqcard.card.model.Theme;
import com.r.qqcard.core.support.AbstractDaoImpl;

/**
 * 主题Dao
 * 
 * @author rain
 *
 */
@Repository("dao.theme")
public class ThemeDaoImpl extends AbstractDaoImpl<Theme> implements ThemeDao {

    public ThemeDaoImpl() {
        super(Theme.class);
    }

}
