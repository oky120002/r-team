/**
 * 
 */
package com.r.qqcard.account.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.qqcard.basedata.service.BaseDataService;
import com.r.qqcard.card.qqhome.QQHomeUser;
import com.r.qqcard.core.component.WebAction;
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
    private static final String BASEDATA_KEYPRE = "service.account.";
    private static final String BASEDATA_KEY_USERNAME = BASEDATA_KEYPRE + "username"; // 默认登录名
    private static final String BASEDATA_KEY_PASSWORD = BASEDATA_KEYPRE + "password"; // 默认密码
    private static final String BASEDATA_KEY_ISKEEPUSERNAMEANDPASSWORD = BASEDATA_KEYPRE + "isKeepUsernameAndPassword"; // 默认是否记住登录名和密码

    private static final String BASEDATA_KEY_NICKNAME = BASEDATA_KEYPRE + "nickname"; // 昵称
    private static final String BASEDATA_KEY_GOLD = BASEDATA_KEYPRE + "gold"; // 金币
    private static final String BASEDATA_KEY_MANA = BASEDATA_KEYPRE + "mana"; // 魔法值

    /** 网络行为 */
    @Resource(name = "component.webaction")
    private WebAction action;

    @Resource(name = "service.basedata")
    private BaseDataService baseDataService;

    /** 获取登录名 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getLoginUsername() {
        return baseDataService.getValueString(BASEDATA_KEY_USERNAME, "");
    }

    /** 获取密码 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getLoginPassword() {
        return baseDataService.getValueString(BASEDATA_KEY_PASSWORD, "");
    }

    /** 获取是否记住登录名和密码 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public boolean isLoginKeepUsernameAndPassword() {
        return baseDataService.getValueBoolean(BASEDATA_KEY_ISKEEPUSERNAMEANDPASSWORD, false);
    }

    /** 获取用户名 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getUsername() {
        return baseDataService.getValueString(BASEDATA_KEY_USERNAME);
    }

    /** 获取昵称 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String getNickName() {
        return baseDataService.getValueString(BASEDATA_KEY_NICKNAME);
    }

    /** 获取金币 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public int getGold() {
        return baseDataService.getValueInteger(BASEDATA_KEY_GOLD);
    }

    /** 获取魔法值 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public int getMana() {
        return baseDataService.getValueInteger(BASEDATA_KEY_MANA);
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
        baseDataService.save(BASEDATA_KEY_USERNAME, username, "登录名");
        baseDataService.save(BASEDATA_KEY_PASSWORD, password, "密码");
        baseDataService.save(BASEDATA_KEY_ISKEEPUSERNAMEANDPASSWORD, isKeepUsernameAndPassword, "是否保存登录名和密码");
    }

    /** 初始化账号信息 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void initAccount(QQHomeUser user) {
        baseDataService.setValue(BASEDATA_KEY_NICKNAME, user.getNick()); // 昵称
        baseDataService.setValue(BASEDATA_KEY_GOLD, Integer.toString(user.getMana())); // 金币
        baseDataService.setValue(BASEDATA_KEY_MANA, Integer.toString(user.getMana())); // 魔法值
    }
}
