/**
 * 
 */
package com.r.qqcard.core.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.qqcard.core.serivce.InitService;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * 初始化控制器
 * 
 * @author rain
 *
 */
@Controller("controller.core")
public class CoreController {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(CoreController.class);

    /** 初始化业务处理器 */
    @Resource(name = "service.init")
    private InitService initService;

    /** 全局数据初始化,登陆前的初始化工作 */
    @EventAnn(Event.core$全局数据初始化)
    public void initGlobal() {
        initService.initGlobal();
    }

    @EventAnn(Event.core$玩家信息初始化)
    public void initGameDatas() {
        initService.initGameDatas();
    }

    /** 启动自动炼卡功能 */
    @EventAnn(Event.core$启动自动炼卡)
    public void startAuto() {
        logger.info("启动自动炼卡......");
    }
}
