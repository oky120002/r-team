package com.r.app.taobaoshua;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.r.app.taobaoshua.view.TaoBaoLoginFrame;
import com.r.core.desktop.ctrl.HCtrlUtil;

/**
 * Hello world!
 * 
 */
public class TaoBaoApp {

    private static TaoBaoApp app;
    private ApplicationContext applicationContext; // spring容器
    private TaoBaoLoginFrame loginFrame; // 登陆窗口

    /** 获取ApplicationContext */
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /** 返回唯一实例 */
    public static final TaoBaoApp getTaoBaoApp() {
        return TaoBaoApp.app;
    }
    
    /** 获取淘宝刷图标 */
    public static Image getDefaultIco() throws IOException {
        String imagepath = "com/r/app/taobaoshua/image/tray.png";
        InputStream ins = ClassLoader.getSystemResourceAsStream(imagepath);
        return ImageIO.read(ins);
    }

    /** 启动 */
    private static void startup() {
        TaoBaoApp.app = new TaoBaoApp();

        // 初始化spring容器
        TaoBaoApp.app.applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        // 实例化登陆窗口
        TaoBaoApp.app.loginFrame = new TaoBaoLoginFrame();
        TaoBaoApp.app.loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
        HCtrlUtil.setNoSpot();
        HCtrlUtil.setUIFont(null);
        HCtrlUtil.setWindowsStyleByWindows(null);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TaoBaoApp.startup(); // 启动
            }
        });
    }
}
