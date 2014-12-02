/**
 * 
 */
package com.r.qqcard.account.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.qqcard.basedata.domain.BaseData;
import com.r.qqcard.basedata.service.BaseDataService;
import com.r.qqcard.core.support.AbstractService;

/**
 * 账号业务类
 * 
 * @author rain
 *
 */
@Service("service.account")
public class AccountService extends AbstractService {
    /** 基础数据key前缀 */
    private static final String BASEDATA_KEYPRE = "_account.";
    private static final String BASEDATA_KEY_USERNAME = BASEDATA_KEYPRE + "username"; // 默认登录名
    private static final String BASEDATA_KEY_PASSWORD = BASEDATA_KEYPRE + "password"; // 默认密码
    private static final String BASEDATA_KEY_ISKEEPUSERNAMEANDPASSWORD = BASEDATA_KEYPRE + "isKeepUsernameAndPassword"; // 默认是否记住登录名和密码

    @Resource(name = "service.basedata")
    private BaseDataService baseDataService;

    /** 获取用户名 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getUsername() {
        BaseData data = baseDataService.find(BASEDATA_KEY_USERNAME);
        if (data == null) {
            return null;
        }
        return data.getValue();
    }

    /** 获取密码 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getPassword() {
        BaseData data = baseDataService.find(BASEDATA_KEY_PASSWORD);
        if (data == null) {
            return null;
        }
        return data.getValue();
    }

    /** 是否记录登录名和密码 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public boolean isKeepUsernameAndPassword() {
        BaseData data = baseDataService.find(BASEDATA_KEY_ISKEEPUSERNAMEANDPASSWORD);
        if (data == null) {
            return false;
        }
        return data.getValueByBoolean();
    }

    /**
     * 设置登陆时的用户名和密码
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param isKeepUsernameAndPassword
     *            是否记录用户名和密码
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void setUsernameAndPassword(String username, String password, boolean isKeepUsernameAndPassword) {
        baseDataService.save(new BaseData(BASEDATA_KEY_USERNAME, username, "登录名"));
        baseDataService.save(new BaseData(BASEDATA_KEY_PASSWORD, password, "密码"));
        baseDataService.save(new BaseData(BASEDATA_KEY_ISKEEPUSERNAMEANDPASSWORD, isKeepUsernameAndPassword, "是否保存登录名和密码"));

        logger.info("测试 - 用户名:{}", getUsername());
    }
}
