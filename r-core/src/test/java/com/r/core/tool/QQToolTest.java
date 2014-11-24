package com.r.core.tool;

import java.awt.Image;

import org.junit.Test;

import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel.ShowMode;
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
    private static final HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
    /** QQ账号 */
    private static final String username = "14006297";
    /** 密码 */
    private static final String password = "yuyu0619@qq.com";
    /** QQ网络应用唯一标识-QQ魔法卡片 */
    private static final String appid = "10000101";

    @Test
    public void test() {
        Image verifycodeImage = QQTool.getLoginWebVerifycodeImage(httpSocket, appid, username);
        ImageTool.showImage(verifycodeImage, ShowMode.平铺);

        String verifycode = HAlert.showInput("请输入校验码");
        logger.debug("检验码:" + verifycode);

        String loginWeb = QQTool.loginWeb(httpSocket, appid, username, password, verifycode);
        logger.debug("登陆信息 : {}", loginWeb);
    }
}
