package com.r.core.tool;

import java.awt.Dimension;
import java.awt.Image;

import org.junit.Test;

import com.r.core.desktop.ctrl.HCtrlUtil;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.dialog.HLoginDialog.HLoginHandler;
import com.r.core.desktop.ctrl.obtain.HImageObtain;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * QQ工具测试类<br/>
 * QQ魔法卡片Appid:10000101
 * 
 * @author rain
 *
 */
public class QQToolTest implements HImageObtain, HLoginHandler {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(QQToolTest.class, "QQ工具测试类");
    /** 套接字 */
    private static final HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
    /** QQ账号 */
    private static final String username = "2912617246";
    /** 密码 */
    private static final String password = "oky120002";
    /** QQ网络应用唯一标识-QQ魔法卡片 */
    private static final String appid = "10000101";

    {
        HCtrlUtil.init(); // 初始化控件行为
        httpSocket.setTimeout(10 * 000); // 设置超时时间.10秒
    }

    /** 登陆测试 */
    @Test
    public void test() {
        logger.debug("进行登陆测试.");
        boolean isLogin = HAlert.showLoginDialog("登陆QQ账户", this, this);
        if (isLogin) {
            logger.debug("登陆信息 : {}", "成功登陆!");
        } else {
            logger.debug("登陆信息 : {}", "登陆失败!");
        }
    }

    /** 验证码获取测试 */
    public void testVerifycode() {
        logger.debug("进行验证码获取测试.");
        String verifycode = HAlert.showAuthCodeDialog(this);
        logger.debug("检验码:" + verifycode);
    }

    @Override
    public Image getHImage() {
        try {
            return QQTool.getLoginWebVerifycodeImage(httpSocket, appid, username);
        } catch (NetworkIOReadErrorException e) {
            HAlert.showErrorTips(e, null);
        }
        return null;
    }

    @Override
    public Dimension getHImageSize() {
        return new Dimension(130, 55);
    }

    @Override
    public String doLogin(String username, String password, String authCode) {
        return QQTool.loginWeb(httpSocket, appid, username, password, authCode);
    }
}
