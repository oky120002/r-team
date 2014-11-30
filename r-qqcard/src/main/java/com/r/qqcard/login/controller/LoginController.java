/**
 * 
 */
package com.r.qqcard.login.controller;

import java.awt.Image;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.alert.HAlert.LoginReturnValueObtain;
import com.r.core.desktop.ctrl.impl.dialog.HLoginDialog.LoginStatus;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.qqcard.App;
import com.r.qqcard.account.AccountService;
import com.r.qqcard.context.QQCardContext;
import com.r.qqcard.notify.context.NotifyContext;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * 登陆界面控制器
 * 
 * @author rain
 *
 */
@Controller("controller.login")
public class LoginController {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /** QQ卡片辅助程序容器 */
    @Resource(name = "springxml.context")
    private QQCardContext context;
    /** 通知 */
    @Resource(name = "springxml.notify")
    private NotifyContext notify;
    /** 账号业务处理器 */
    @Resource(name = "service.account")
    private AccountService accountService;

    /** 显示登陆对话框 */
    @EventAnn(Event.程序启动)
    public void showLoginDialog() {
        notify.notifyEvent(Event.登陆前);
        HttpSocket httpSocket = context.getHttpSocket();
        String appid = context.getAppid();
        String defaultUsername = accountService.getDefaultUsername();
        String defaultPassword = accountService.getDefaultPassword();
        HAlert.showLoginDialogByQQ(httpSocket, appid, defaultUsername, defaultPassword, new LoginReturnValueObtain() {
            @Override
            public void returnValue(LoginStatus loginStatus, String username, String password, Image image, boolean isKeepUsernameAndPassword) {
                if (LoginStatus.成功登陆.equals(loginStatus)) {
                    accountService.setDefaultUsernameAndPassword(username, password, isKeepUsernameAndPassword);
                    notify.notifyEvent(Event.登陆成功, username, password, isKeepUsernameAndPassword);
                    logger.info("登陆QQ......");
                    logger.info("成功登陆账号[{}]......", username);
                } else {
                    notify.notifyEvent(Event.登陆失败, username, password, isKeepUsernameAndPassword);
                    App.getInstance().exit();
                }
            }
        });
    }

}
