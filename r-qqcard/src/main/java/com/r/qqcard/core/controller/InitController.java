/**
 * 
 */
package com.r.qqcard.core.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.r.qqcard.core.serivce.InitService;
import com.r.qqcard.notify.handler.Event;
import com.r.qqcard.notify.handler.EventAnn;

/**
 * 初始化控制器
 * 
 * @author rain
 *
 */
@Controller("controller.init")
public class InitController {

    /** 初始化业务处理器 */
    @Resource(name = "service.init")
    private InitService initService;

    /** 全局数据初始化,登陆前的初始化工作 */
    @EventAnn(Event.init$全局数据初始化)
    public void initGlobal() {
        initService.initGlobal();
    }

    @EventAnn(Event.init$玩家信息初始化)
    public void initGameDatas() {
        initService.initGameDatas();
    }
}
