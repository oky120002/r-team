/**
 * 
 */
package com.r.qqcard.card.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.log.LoggerListener;
import com.r.core.util.RandomUtil;
import com.r.qqcard.account.service.AccountService;
import com.r.qqcard.account.service.AccountService.AccountEnum;
import com.r.qqcard.card.view.MainFrame;
import com.r.qqcard.context.QQCardContext;
import com.r.qqcard.core.serivce.InitService;
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
        this.mainFrame = new MainFrame(this, context.getAppName() + " " + context.getAppVersion(), context.isDebug());
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

    /** 显示主界面窗口 */
    public void showMainFrame() {
        this.mainFrame.setVisible(true);
    }

    /** 启动自动炼卡 */
    public void startAutoStove() {
        this.notify.notifyEvent(Event.core$启动自动炼卡);
    }

    /** 显示卡片箱子对话框 */
    public void showCardBoxDialog() {
        this.notify.notifyEvent(Event.box$显示卡片箱子对话框);
    }

    /** 显示卡片图片对话框 */
    public void showCardImageDialog() {
        this.notify.notifyEvent(Event.cardimage$显示卡片图片对话框);
    }

    /** 同步数据 */
    public void synchronizedGameDatas() {
        String eventName = RandomUtil.randomString(16);
        this.notify.notifySynchronizedEvent(Event.core$玩家信息初始化, eventName);
        this.notify.notifySynchronizedEvent(Event.core$同步数据, eventName);
    }

    @EventAnn(Event.login$登陆完成)
    public void loginEnd(Boolean isLogin, String username, String password, Boolean isflg) {
        if (Boolean.TRUE.equals(isLogin)) {
            showMainFrame();
        }
    }

    @EventAnn(Event.core$全局数据初始化完成)
    public void initGlobalEnd() {
        synchronized (this) {
            isInitGlobal = true;
            startupGame();
        }
    }

    @EventAnn(Event.core$玩家信息初始化完成)
    public void initGameDatasEnd() {
        synchronized (this) {
            isInitGameData = true;
            startupGame();
            initGameDatas();
        }
    }

    /** 启动游戏(解除各种功能的限制,必须在全局数据和游戏数据都加在完成后) */
    private void startupGame() {
        if (isInitGameData && isInitGlobal) {
            logger.info("初始化游戏信息完成......");
            this.mainFrame.startupGame();
        }
    }

    /** 初始化游戏数据 */
    private void initGameDatas() {
        this.mainFrame.setNickName(accountService.getValueString(AccountEnum.昵称, "null"));
        this.mainFrame.setGold(accountService.getValueString(AccountEnum.金币, "-1"));
        this.mainFrame.setMana(accountService.getValueString(AccountEnum.魔法值, "-1"));
        this.mainFrame.setLevel(accountService.getValueString(AccountEnum.等级, "-1"));
        this.mainFrame.setExp(accountService.getValueString(AccountEnum.经验值, "-1"), accountService.getValueString(AccountEnum.升级经验值, "-1"));
    }
}
