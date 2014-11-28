/**
 * 
 */
package com.r.qqcard.account;

import org.springframework.stereotype.Service;

/**
 * 账号业务类
 * 
 * @author rain
 *
 */
@Service("service.account")
public class AccountService {

    /** 获取默认用户名 */
    public String getDefaultUsername() {
        // TODO Auto-generated method stub
        return null;
    }

    /** 获取默认密码 */
    public String getDefaultPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    /** 是否记录登录名和密码 */
    public boolean isKeepUsernameAndPassword() {
        return false;
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
    public void setDefaultUsernameAndPassword(String username, String password, boolean isKeepUsernameAndPassword) {

    }
}
