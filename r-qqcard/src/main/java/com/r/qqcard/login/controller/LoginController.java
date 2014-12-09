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
import com.r.qqcard.account.service.AccountService;
import com.r.qqcard.account.service.AccountService.AccountEnum;
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
    /** 网络请求套接字 */
    @Resource(name = "springxml.httpsocket")
    private HttpSocket httpSocket;
    /** 账号业务处理器 */
    @Resource(name = "service.account")
    private AccountService accountService;

    /** 显示登陆对话框 */
    @EventAnn(Event.login$程序启动)
    public void showLoginDialog() {
        logger.info("QQ卡片程序启动......");
        String appid = context.getAppid();
        String defaultUsername = accountService.getValueString(AccountEnum.登录名, null);
        String defaultPassword = accountService.getValueString(AccountEnum.密码, null);
        boolean loginKeepUsernameAndPassword = accountService.getValueBoolean(AccountEnum.记住登录名和密码, false);
        HAlert.showLoginDialogByQQ(httpSocket, appid, defaultUsername, defaultPassword, loginKeepUsernameAndPassword, new LoginReturnValueObtain() {
            @Override
            public void returnValue(LoginStatus loginStatus, String username, String password, Image image, boolean isKeepUsernameAndPassword) {
                if (LoginStatus.成功登陆.equals(loginStatus)) {
                    accountService.setUsernameAndPassword(username, password, isKeepUsernameAndPassword);
                    notify.notifyEvent(Event.login$登陆完成, Boolean.TRUE, username, password, isKeepUsernameAndPassword);
                    notify.notifyEvent(Event.init$玩家信息初始化);
                    logger.info("成功登陆账号[{}]......", username);
                } else {
                    notify.notifyEvent(Event.login$登陆完成, Boolean.FALSE, username, password, isKeepUsernameAndPassword);
                    App.getInstance().exit();
                }
            }
        });
    }
}
