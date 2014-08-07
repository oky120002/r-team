/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.sample.yy95;

import java.awt.EventQueue;
import java.io.IOException;

import com.r.core.desktop.ctrl.HCtrlUtil;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
public class YY95 {

    public static void main(String[] args) throws IOException {
        HCtrlUtil.setNoSpot();
        HCtrlUtil.setUIFont(null);
        HCtrlUtil.setWindowsStyleByWindows(null);
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                new YY95Frame().setVisible(true);
            }
        });
    }
}
