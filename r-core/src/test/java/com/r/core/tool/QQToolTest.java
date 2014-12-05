package com.r.core.tool;

import java.awt.Dimension;
import java.awt.Image;

import org.junit.Test;

import com.r.core.desktop.ctrl.HCtrlUtil;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.dialog.HLoginDialog.LoginStatus;
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
public class QQToolTest {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(QQToolTest.class, "QQ工具测试类");
    /** 套接字 */
    private static final HttpSocket HTTPSOCKET = HttpSocket.newHttpSocket(true, null);
    /** QQ账号 */
    private static final String USERNAME = "2912617246";
    /** 密码 */
    private static final String PASSWORD = "oky120002";
    /** QQ网络应用唯一标识-QQ魔法卡片 */
    private static final String APPID = "10000101";

    {
        HCtrlUtil.init(); // 初始化控件行为
        HTTPSOCKET.setTimeout(10 * 000); // 设置超时时间.10秒
    }

    /** 登陆测试 */
    @Test
    public void testLogin() {
        logger.debug("进行登陆测试.");
        LoginStatus loginstatus = HAlert.showLoginDialogByQQ(HTTPSOCKET, APPID, USERNAME, PASSWORD, true, null);

        switch (loginstatus) {
        case 成功登陆:
            logger.debug("登陆信息 : {}", "成功登陆!");
            HAlert.showTips("登陆成功", "登陆结果", null);
            break;
        case 未知错误:
            logger.debug("登陆信息 : {}", "未知原因,登陆失败!");
            HAlert.showTips("未知原因,登陆失败", "登陆结果", null);
            break;
        default:
            logger.debug("登陆信息 : {}", loginstatus.name());
            HAlert.showTips(loginstatus.name(), "登陆结果", null);
            break;
        }
    }

    /** 验证码获取测试 */
    // @Test
    public void testVerifycode() {
        logger.debug("进行验证码获取测试.");
        String verifycode = HAlert.showAuthCodeDialog(new HImageObtain() {
            @Override
            public Dimension getHImageSize() {
                return new Dimension(130, 55);
            }

            @Override
            public Image getHImage() {
                try {
                    return QQTool.getLoginWebVerifycodeImage(HTTPSOCKET, APPID, USERNAME);
                } catch (NetworkIOReadErrorException e) {
                    HAlert.showErrorTips(e, null);
                }
                return null;
            }
        });
        logger.debug("检验码:" + verifycode);
        HAlert.showTips(verifycode, "验证码", null);
    }
}
