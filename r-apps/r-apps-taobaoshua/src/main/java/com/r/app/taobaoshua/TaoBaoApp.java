package com.r.app.taobaoshua;

import java.awt.EventQueue;

import com.r.core.desktop.ctrl.HCtrlUtil;

/**
 * Hello world!
 * 
 */
public class TaoBaoApp {
    
    public static void main(String[] args) {
        HCtrlUtil.setNoSpot();
        HCtrlUtil.setUIFont(null);
        HCtrlUtil.setWindowsStyleByWindows(null);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TaobaoShuaApp.startup(); // 启动
            }
        });
    }
}
