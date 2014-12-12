/**
 * 
 */
package com.r.qqcard.account.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.core.util.AssertUtil;
import com.r.qqcard.basedata.service.BaseDataService;
import com.r.qqcard.card.qqhome.QQEXP;
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

    /** 网络行为 */
    @Resource(name = "component.webaction")
    private WebAction action;
    /** 基础数据业务处理类 */
    @Resource(name = "service.basedata")
    private BaseDataService baseDataService;

    /** 设置账户中的值 */
    public void setValue(AccountEnum accountEnum, Object value) {
        AssertUtil.isNotNull(accountEnum);
        baseDataService.setValue(accountEnum.getKey(), value);
    }

    /** 获取账户中的字符串值 */
    public String getValueString(AccountEnum accountEnum, String defaultValue) {
        AssertUtil.isNotNull(accountEnum);
        return baseDataService.getValueString(accountEnum.getKey(), defaultValue);
    }

    /** 获取账户中的整数值 */
    public int getValueInteger(AccountEnum accountEnum, int defaultValue) {
        AssertUtil.isNotNull(accountEnum);
        return baseDataService.getValueInteger(accountEnum.getKey(), defaultValue);
    }

    /** 获取账户中的布尔值 */
    public boolean getValueBoolean(AccountEnum accountEnum, boolean defaultValue) {
        AssertUtil.isNotNull(accountEnum);
        return baseDataService.getValueBoolean(accountEnum.getKey(), defaultValue);
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
        baseDataService.setValue(AccountEnum.登录名.getKey(), username);
        baseDataService.setValue(AccountEnum.密码.getKey(), password);
        baseDataService.setValue(AccountEnum.记住登录名和密码.getKey(), isKeepUsernameAndPassword);
    }

    /** 初始化账号信息 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void initAccount(QQHomeUser user) {
        baseDataService.setValue(AccountEnum.昵称.getKey(), user.getNick()); // 昵称
        baseDataService.setValue(AccountEnum.金币.getKey(), user.getMana()); // 金币
        baseDataService.setValue(AccountEnum.魔法值.getKey(), user.getMana()); // 魔法值
        baseDataService.setValue(AccountEnum.等级.getKey(), user.getLv()); // 等级
        baseDataService.setValue(AccountEnum.经验值.getKey(), user.getExp()); // 经验值
        baseDataService.setValue(AccountEnum.升级经验值.getKey(), QQEXP.values()[user.getLv() - 1].getExp()); // 经验值
    }

    public enum AccountEnum {
        登录名("username"), 密码("password"), 记住登录名和密码("isKeepUsernameAndPassword"), //
        昵称("nickname"), 金币("gold"), 魔法值("mana"), 等级("level"), 经验值("exp"), 升级经验值("expLevel")//
        ;

        private static final String BASEDATA_KEYPRE = "service.account.";

        private String key;

        public String getKey() {
            return key;
        }

        AccountEnum(String key) {
            this.key = BASEDATA_KEYPRE + key;
        }
    }
}
