/**
 * 
 */
package com.r.qqcard.basedata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.qqcard.basedata.dao.BaseDataDao;
import com.r.qqcard.basedata.domain.BaseData;
import com.r.qqcard.core.support.AbstractService;

/**
 * 基础数据业务处理器
 * 
 * @author rain
 *
 */
@Service("service.basedata")
public class BaseDataService extends AbstractService {

    @Resource(name = "dao.basedata")
    private BaseDataDao baseDataDao;

    /** 保存基础数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void save(BaseData data) {
        baseDataDao.save(data);
    }

    /** 查找基础数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public BaseData find(String key) {
        return baseDataDao.find(key);
    }
}
