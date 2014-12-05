/**
 * QQ卡片辅助程序
 */
package com.r.qqcard;

import java.awt.EventQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.r.core.desktop.ctrl.HCtrlUtil;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.qqcard.notify.context.NotifyContext;
import com.r.qqcard.notify.handler.Event;

/** QQ卡片辅助程序入口 */
public class App implements Runnable {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(App.class, "QQCard");

    /** QQ卡片辅助程序实例 */
    private static App app = null;
    /** Spring 容器 */
    private ApplicationContext applicationContext = null;

    public App() {
        super();
        logger.info("QQCardApp 实例化 ......");
    }

    public static void main(String[] args) {
        logger.debug("QQ卡片辅助程序（进入main方法） ......");
        HCtrlUtil.init(); // 初始化控件行为
        LoggerFactory.setDefaultPre("QQCard"); // 设置日志默认前缀
        EventQueue.invokeLater(new App());// 启动
    }

    /** 返回此应用的唯一实例 */
    public static final App getInstance() {
        return App.app;
    }

    /** 获取spring容器 */
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public void run() {
        App.app = this;
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        NotifyContext notify = (NotifyContext) applicationContext.getBean("springxml.notify");
        notify.notifyEvent(Event.login$程序启动);
        notify.notifyEvent(Event.init$全局数据初始化);
    }

    /** 退出QQ卡片辅助程序 */
    public void exit() {
        System.exit(0);
    }
}
