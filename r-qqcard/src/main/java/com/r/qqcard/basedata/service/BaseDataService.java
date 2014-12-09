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

    /** 根据Key获取基础数据的字符串值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getValueString(String key, String defaultValue) {
        BaseData data = baseDataDao.find(key);
        return data == null ? defaultValue : data.getValue();
    }

    /** 根据Key获取基础数据的整数值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public int getValueInteger(String key, int defaultValue) {
        BaseData data = baseDataDao.find(key);
        return data == null ? defaultValue : Integer.valueOf(data.getValue()).intValue();
    }

    /** 根据Key获取基础数据的布尔值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public boolean getValueBoolean(String key, boolean defaultValue) {
        BaseData data = baseDataDao.find(key);
        return data == null ? defaultValue : Boolean.valueOf(data.getValue()).booleanValue();
    }

    /** 保存基础数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void setValue(String key, Object value) {
        BaseData data = new BaseData();
        data.setKey(key);
        data.setValue(value == null ? null : value.toString());
        baseDataDao.save(data);
    }
}
