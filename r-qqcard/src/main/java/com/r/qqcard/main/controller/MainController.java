/**
 * 
 */
package com.r.qqcard.main.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.qqcard.main.view.MainFrame;

/**
 * 主界面控制器
 * 
 * @author rain
 *
 */
@Controller("controller.main")
public class MainController implements InitializingBean {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    /** 主界面 */
    private MainFrame mainFrame;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.mainFrame = new MainFrame();
    }

    /** 显示主界面窗口 */
    public void showMainFrame() {
        logger.info("显示主界面......");

    }

}
