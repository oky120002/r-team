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
import com.r.qqcard.main.controller.MainController;

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

    /** 账号业务类 */
    @Resource(name = "service.account")
    private AccountService accountService;

    /** 主界面控制器 */
    @Resource(name = "controller.main")
    private MainController mainController;

    /** 显示登陆对话框 */
    public void showLoginDialog() {
        logger.info("登陆QQ......");
        HttpSocket httpSocket = context.getHttpSocket();
        String appid = context.getAppid();
        String defaultUsername = accountService.getDefaultUsername();
        String defaultPassword = accountService.getDefaultPassword();
        HAlert.showLoginDialogByQQ(httpSocket, appid, defaultUsername, defaultPassword, new LoginReturnValueObtain() {
            @Override
            public void returnValue(LoginStatus loginStatus, String username, String password, Image image, boolean isKeepUsernameAndPassword) {
                if (LoginStatus.成功登陆.equals(loginStatus)) {
                    logger.info("成功登陆账号[{}]......", username);
                    accountService.setDefaultUsernameAndPassword(username, password, isKeepUsernameAndPassword);
                    mainController.showMainFrame();
                } else {
                    App.getInstance().exit();
                }
            }
        });
    }
}
