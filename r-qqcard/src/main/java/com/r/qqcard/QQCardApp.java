/**
 * QQ卡片辅助程序
 */
package com.r.qqcard;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.r.core.desktop.ctrl.HCtrlUtil;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/** QQ卡片辅助程序入口 */
public class QQCardApp {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(QQCardApp.class);

    /** QQ卡片辅助程序实例 */
    private static QQCardApp app = null; // 应用程序

    public QQCardApp() {
        super();
        logger.info("QQCardApp 实例化 ......");
    }

    /** 返回此应用的唯一实例 */
    public static final QQCardApp getInstance() {
        return QQCardApp.app;
    }

    /** 启动QQ卡片辅助程序 */
    public static void startup() {
        QQCardApp.app = new QQCardApp();
    }

    public static void main(String[] args) {
        logger.debug("main ......");
        HCtrlUtil.init();
        System.out.println(UIManager.getDefaults().size());
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                QQCardApp.startup(); // 启动
            }
        });
    }
}
