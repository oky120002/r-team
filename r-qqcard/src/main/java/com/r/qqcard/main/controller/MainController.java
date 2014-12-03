/**
 * 
 */
package com.r.qqcard.main.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.log.LoggerListener;
import com.r.qqcard.context.QQCardContext;
import com.r.qqcard.main.view.MainFrame;
import com.r.qqcard.notify.context.NotifyContext;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * 主界面控制器
 * 
 * @author rain
 *
 */
@Controller("controller.main")
public class MainController implements InitializingBean, LoggerListener {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    /** 时间格式化类 */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 主界面 */
    private MainFrame mainFrame;
    /** QQ卡片辅助程序容器 */
    @Resource(name = "springxml.context")
    private QQCardContext context;
    /** 通知 */
    @Resource(name = "springxml.notify")
    private NotifyContext notify;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("添加日志监听器......");
        LoggerFactory.addLoggerListener(this);
        this.mainFrame = new MainFrame(context.getAppName() + " " + context.getAppVersion());
    }

    /** 显示主界面窗口 */
    public void showMainFrame() {
        logger.info("显示主界面......");
        this.mainFrame.setVisible(true);
        logger.info("初始化基础数据......");
        notify.notifyEvent(Event.初始化数据); // 初始化数据完成后,才能完全使用
    }

    @Override
    public void log(String loglevel, String pre, String message, Throwable e) {
        if (Logger.LOGGER_LEVEL_INFO.equals(loglevel)) {
            StringBuilder sb = new StringBuilder(dateFormat.format(new Date()));
            sb.append(" ");
            sb.append(message);
            this.mainFrame.printlnInfo(sb.toString());
        }
    }

    @EventAnn(Event.登陆成功)
    public void loginOk(String username, String password, Boolean isflg) {
        showMainFrame();
    }

    @EventAnn(Event.初始化数据完成)
    public void endInit() {
        logger.info("初始化基础数据完成......");
    }

}
