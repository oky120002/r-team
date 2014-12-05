/**
 * 
 */
package com.r.qqcard.main.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.log.LoggerListener;
import com.r.core.util.StrUtil;
import com.r.qqcard.account.service.AccountService;
import com.r.qqcard.context.QQCardContext;
import com.r.qqcard.core.serivce.InitService;
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

    /** QQ卡片辅助程序容器 */
    @Resource(name = "springxml.context")
    private QQCardContext context;
    /** 通知 */
    @Resource(name = "springxml.notify")
    private NotifyContext notify;
    /** 账户业务处理器 */
    @Resource(name = "service.account")
    private AccountService accountService;
    /** 初始化业务处理器 */
    @Resource(name = "service.init")
    private InitService initService;
    /** 全局数据是否已经初始化完毕 */
    private boolean isInitGlobal = false;
    /** 游戏数据是否已经初始化完毕 */
    private boolean isInitGameData = false;

    /** 主界面 */
    private MainFrame mainFrame;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("添加日志监听器......");
        LoggerFactory.addLoggerListener(this);
        this.mainFrame = new MainFrame(context.getAppName() + " " + context.getAppVersion());
    }

    /** 显示主界面窗口 */
    public void showMainFrame() {
        this.mainFrame.setVisible(true);
    }

    /** 记录日志 */
    @Override
    public void log(String loglevel, String pre, String message, Throwable e) {
        if (Logger.LOGGER_LEVEL_INFO.equals(loglevel)) {
            StringBuilder sb = new StringBuilder(dateFormat.format(new Date()));
            sb.append(" ");
            sb.append(message);
            this.mainFrame.printlnInfo(sb.toString());
        }
    }

    @EventAnn(Event.login$登陆完成)
    public void loginOk(Boolean isLogin, String username, String password, Boolean isflg) {
        if (Boolean.TRUE.equals(isLogin)) {
            showMainFrame();
        }
    }

    @EventAnn(Event.init$全局数据初始化完成)
    public void initGlobalEnd() {
        logger.info("初始化基础数据完成......");
        isInitGlobal = true;
        startupGame();
    }

    @EventAnn(Event.init$玩家信息初始化完成)
    public void initGameDatasEnd() {
        logger.info("更新交换箱信息，卡箱信息，账号信息完成......");
        isInitGameData = true;
        startupGame();
    }

    /** 启动游戏(解除各种功能的限制) */
    private void startupGame() {
        if (isInitGameData && isInitGlobal) {
            String nickName = accountService.getNickName();
            int gold = accountService.getGold();
            int mana = accountService.getMana();
            HAlert.showTips(StrUtil.formart("获取昵称:{},金币:{},魔法值:{}", nickName, gold, mana), "启动", mainFrame);
        }
    }
}
