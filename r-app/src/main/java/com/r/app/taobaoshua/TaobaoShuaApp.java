/**
 * 
 */
package com.r.app.taobaoshua;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.r.app.core.util.ImageUtil;
import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.core.Core;
import com.r.app.taobaoshua.taobao.TaoBao;
import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.desktop.ctrl.HCtrlUtil;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TrayUtil;

/**
 * 淘宝刷
 * 
 * @author Administrator
 * 
 */
public class TaobaoShuaApp {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoShuaApp.class);
    private static final String COMMAND_TRAY_YUUBOO_INIT = "command_tray_yuuboo_init";
    private static final String COMMAND_TRAY_EXIT = "command_tray_exit";
    // private static final String COMMAND_TAOBAO_INIT = "command_taobao_init";
    private static final String COMMAND_TRAY_CORE_INIT = "command_core_init";

    private static TaobaoShuaApp app = null; // 应用程序
    private TaobaoShuaDesktop desktop;
    private Core core;
    private YuuBoo yuuBoo;
    private TaoBao taoBao;
    private BlueSky blueSky;

    private TaobaoShuaApp() {
        super();
        logger.info("TaobaoShuaApp newInstance  ................");
    }

    /** 返回此应用的唯一实例 */
    public static final TaobaoShuaApp getInstance() {
        return TaobaoShuaApp.app;
    }

    public Core getCore() {
        return this.core;
    }

    public YuuBoo getYuuBoo() {
        return this.yuuBoo;
    }

    public TaoBao getTaoBao() {
        return this.taoBao;
    }

    public BlueSky getBlueSky() {
        return blueSky;
    }

    public TaobaoShuaDesktop getMainDesktop() {
        return desktop;
    }

    /** 返回众多代理中的下一个,出现过的代理不在返回 */
    public HttpProxy getNextProxy() {
        return null;
        // return HttpProxy.newInstance(true, Type.HTTP, "122.96.59.102", 83);
    }

    /** 启动 */
    public static void startup() {
        TaobaoShuaApp.app = new TaobaoShuaApp();
        TaobaoShuaApp.app.core = new Core();
        TaobaoShuaApp.app.yuuBoo = new YuuBoo();
        TaobaoShuaApp.app.taoBao = new TaoBao();
        TaobaoShuaApp.app.blueSky = new BlueSky();
        try {
            TaobaoShuaApp.app.init();
        } catch (Exception e) {
            HAlert.showErrorTips(e.toString(), null, e);
        }
        TaobaoShuaApp.app.core.init();
        LoggerFactory.addLoggerListener(TaobaoShuaApp.app.core);
        TaobaoShuaApp.app.core.startup();

        TaobaoShuaApp.app.taoBao.init();
        TaobaoShuaApp.app.yuuBoo.init();
        TaobaoShuaApp.app.blueSky.init();
        TaobaoShuaApp.app.taoBao.getAction().setSocketProxy(TaobaoShuaApp.app.getNextProxy());
        TaobaoShuaApp.app.yuuBoo.getAction().setSocketProxy(TaobaoShuaApp.app.getNextProxy());
        TaobaoShuaApp.app.blueSky.getService().setSocketProxy(TaobaoShuaApp.app.getNextProxy());
        TaobaoShuaApp.app.desktop = new TaobaoShuaDesktop("淘宝刷.....");
        TaobaoShuaApp.app.desktop.setVisible(true);
    }

    /** 创建托盘图标 */
    private void init() {

        try {
            Image image = ImageUtil.getDefaultIco();
            TrayUtil.initTray(image, "淘宝刷", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (TaobaoShuaApp.app.desktop.isVisible()) {
                        TaobaoShuaApp.app.desktop.setVisible(false);
                    } else {
                        TaobaoShuaApp.app.desktop.setVisible(true);
                    }
                }
            }, null);
        } catch (IOException ioe) {
            logger.warn("托盘图标加载失败", ioe);
        }
        // 淘宝
        TrayUtil.addTrayMenuItem(COMMAND_TRAY_YUUBOO_INIT, "start yuuboo  (中文)", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TaobaoShuaApp.app.yuuBoo != null && TaobaoShuaApp.app.yuuBoo.isRunning()) {
                    TaobaoShuaApp.app.yuuBoo.getDesktop().setVisible(true);
                }
            }
        });
        // 核心组件
        TrayUtil.addTrayMenuItem(COMMAND_TRAY_CORE_INIT, "start core", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TaobaoShuaApp.app.core != null && TaobaoShuaApp.app.core.isRunning()) {
                    TaobaoShuaApp.app.core.getInfoDialog().setVisible(true);
                }
            }
        });
        // 分隔符
        TrayUtil.addSeparator();
        // 退出
        TrayUtil.addTrayMenuItem(COMMAND_TRAY_EXIT, "exit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "退出淘宝刷?", "淘宝刷", JOptionPane.YES_NO_OPTION)) {
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
                TaobaoShuaApp.startup(); // 启动
            }
        });
    }
}
