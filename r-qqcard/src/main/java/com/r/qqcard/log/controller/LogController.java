/**
 * 
 */
package com.r.qqcard.log.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.log.LoggerListener;

/**
 * 日志控制器
 * 
 * @author rain
 *
 */
@Controller("controller.log")
public class LogController implements InitializingBean, LoggerListener {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        LoggerFactory.addLoggerListener(this); // 添加日志监听器
    }

    @Override
    public void log(String loglevel, String pre, String message, Throwable e) {

    }
}
