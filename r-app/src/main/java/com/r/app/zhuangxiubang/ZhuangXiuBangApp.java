/**
 * 
 */
package com.r.app.zhuangxiubang;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.r.app.core.util.ImageUtil;
import com.r.app.zhuangxiubang.core.Resolve;
import com.r.core.desktop.ctrl.HCtrlUtil;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TrayUtil;

/**
 * 装修帮监视器
 * 
 * @author Administrator
 * 
 */
public class ZhuangXiuBangApp {
    private static final Logger logger = LoggerFactory.getLogger(ZhuangXiuBangApp.class);
    private static final String COMMAND_TRAY_SHOW_MAIN_DESKTOP = "command_tray_show_main_desktop";
    private static final String COMMAND_TRAY_EXIT = "command_tray_exit";

    private static ZhuangXiuBangApp app = null; // 应用程序
    private ZhuangXiuBangDesktop desktop = null;
    private Resolve resolve = null;
    private ApplicationContext applicationContext;

    private ZhuangXiuBangApp() {
        super();
        logger.info("ZhuangXiuBangApp newInstance  ................");
    }

    /** 返回此应用的唯一实例 */
    public static final ZhuangXiuBangApp getInstance() {
        return ZhuangXiuBangApp.app;
    }

    /** 启动 */
    public static void startup() {
        ZhuangXiuBangApp.app = new ZhuangXiuBangApp();
        try {
            ZhuangXiuBangApp.app.init();
        } catch (Exception e) {
            HAlert.showError(e.toString(), null, e);
        }
    }

    /**
     * 创建托盘图标
     * 
     * @throws IOException
     */
    private void init() throws IOException {
        applicationContext = new ClassPathXmlApplicationContext("zhuangxiubang/spring.xml");
        desktop = new ZhuangXiuBangDesktop("装修帮监视器");
        desktop.setVisible(true);
        Image image = ImageUtil.getDefaultIco();
        TrayUtil.initTray(image, "装修帮监视器", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ZhuangXiuBangApp.app.desktop.isVisible()) {
                    ZhuangXiuBangApp.app.desktop.setVisible(false);
                } else {
                    ZhuangXiuBangApp.app.desktop.setVisible(true);
                }
            }
        }, null);
        // 装修帮
        TrayUtil.addTrayMenuItem(COMMAND_TRAY_SHOW_MAIN_DESKTOP, "show main(显示主界面)", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZhuangXiuBangApp.app.desktop.setVisible(true);
            }
        });
        // 分隔符
        TrayUtil.addSeparator();
        // 退出
        TrayUtil.addTrayMenuItem(COMMAND_TRAY_EXIT, "exit(退出)", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "是否退出装修帮监视器?", "装修帮监视器", JOptionPane.YES_NO_OPTION)) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        HCtrlUtil.setNoSpot();
        HCtrlUtil.setUIFont(null);
        HCtrlUtil.setWindowsStyleByWindows(null);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ZhuangXiuBangApp.startup(); // 启动
            }
        });
    }

    public Resolve getResolve() {
        if (resolve == null) {
            resolve = new Resolve();
        }
        return resolve;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
