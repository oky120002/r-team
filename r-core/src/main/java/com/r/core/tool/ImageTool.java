/**
 * 
 */
package com.r.core.tool;

import java.awt.Dimension;
import java.awt.Image;

import com.r.core.desktop.ctrl.impl.dialog.HImageDialog;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel.ShowMode;

/**
 * 图片工具
 * 
 * @author rain
 *
 */
public class ImageTool {

    /**
     * 显示图片<br/>
     * 非常简单弹出一个图片对话框.<br/>
     * 大多数情况下用于调试
     * 
     * @param image
     *            图片
     * @param mode
     *            图片拉伸方式
     * @param isExit
     *            关闭后是否退出程序
     */
    public static final void showImage(Image image, ShowMode mode) {
        HImageDialog dialog = new HImageDialog();
        dialog.setSize(new Dimension(130, 75));
        dialog.setImage(image, mode);
        dialog.setVisible(true);
    }
}
