/**
 * 
 */
package com.r.qqcard.basedata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.core.util.AssertUtil;
import com.r.core.util.StrUtil;
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

    /** 查找基础数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public BaseData find(String key) {
        return baseDataDao.find(key);
    }

    /** 根据Key获取基础数据的字符串值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getValueString(String key) {
        BaseData data = baseDataDao.find(key);
        AssertUtil.isNotNull(StrUtil.formart("找不到key为{}的基础数据", key), data);
        return data == null ? null : data.getValue();
    }

    /** 根据Key获取基础数据的字符串值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getValueString(String key, String defaultValue) {
        BaseData data = baseDataDao.find(key);
        if (data == null) {
            return defaultValue;
        }
        return data == null ? null : data.getValue();
    }

    /** 根据Key获取基础数据的整数值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public int getValueInteger(String key) {
        BaseData data = baseDataDao.find(key);
        AssertUtil.isNotNull(StrUtil.formart("找不到key为{}的基础数据", key), data);
        return data == null ? null : Integer.valueOf(data.getValue()).intValue();
    }

    /** 根据Key获取基础数据的整数值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public int getValueInteger(String key, int defaultValue) {
        BaseData data = baseDataDao.find(key);
        if (data == null) {
            return defaultValue;
        }
        return data == null ? null : Integer.valueOf(data.getValue()).intValue();
    }

    /** 根据Key获取基础数据的布尔值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public boolean getValueBoolean(String key) {
        BaseData data = baseDataDao.find(key);
        AssertUtil.isNotNull(StrUtil.formart("找不到key为{}的基础数据", key), data);
        return data == null ? null : Boolean.valueOf(data.getValue()).booleanValue();
    }

    /** 根据Key获取基础数据的布尔值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public boolean getValueBoolean(String key, boolean defaultValue) {
        BaseData data = baseDataDao.find(key);
        if (data == null) {
            return defaultValue;
        }
        return data == null ? null : Boolean.valueOf(data.getValue()).booleanValue();
    }

    /** 保存基础数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void save(String key, boolean value, String remark) {
        BaseData data = new BaseData();
        data.setKey(key);
        data.setValue(Boolean.toString(value));
        data.setRemark(remark);
        baseDataDao.save(data);
    }

    /** 设置值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void setValue(String key, String value) {
        BaseData data = baseDataDao.find(key);
        AssertUtil.isNotNull(StrUtil.formart("找不到key为{}的基础数据", key), data);
        data.setValue(value);
        baseDataDao.update(data);
    }

    /** 设置值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void setValue(String key, int value) {
        BaseData data = baseDataDao.find(key);
        AssertUtil.isNotNull(StrUtil.formart("找不到key为{}的基础数据", key), data);
        data.setValue(Integer.toString(value));
        baseDataDao.update(data);
    }

    /** 设置值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void setValue(String key, boolean value) {
        BaseData data = baseDataDao.find(key);
        AssertUtil.isNotNull(StrUtil.formart("找不到key为{}的基础数据", key), data);
        data.setValue(Boolean.toString(value));
        baseDataDao.update(data);
    }

    /** 保存基础数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void save(BaseData data) {
        baseDataDao.save(data);
    }

    /** 保存基础数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void save(String key, String value, String remark) {
        BaseData data = new BaseData();
        data.setKey(key);
        data.setValue(value);
        data.setRemark(remark);
        baseDataDao.save(data);
    }

    /** 保存基础数据 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void save(String key, int value, String remark) {
        BaseData data = new BaseData();
        data.setKey(key);
        data.setValue(Integer.toString(value));
        data.setRemark(remark);
        baseDataDao.save(data);
    }
}
