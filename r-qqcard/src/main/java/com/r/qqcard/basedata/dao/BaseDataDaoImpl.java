/**
 * 
 */
package com.r.qqcard.basedata.dao;

import org.springframework.stereotype.Repository;

import com.r.qqcard.basedata.domain.BaseData;
import com.r.qqcard.core.support.AbstractDaoImpl;

/**
 * @author rain
 *
 */
@Repository("dao.basedata")
public class BaseDataDaoImpl extends AbstractDaoImpl<BaseData> implements BaseDataDao {

    public BaseDataDaoImpl() {
        super(BaseData.class);
    }

}
