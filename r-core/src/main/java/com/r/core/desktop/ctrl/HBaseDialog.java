/**
 * 修  改   人:  Administrator
 * 修改时间:  2013-1-3
 * <修改描述:>
 */
package com.r.core.desktop.ctrl;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;

import com.r.core.desktop.ctrl.plugin.HDragPlugin;

/**
 * 对话框
 * 
 * @author oky
 * 
 */
public class HBaseDialog extends JDialog {
    private static final long serialVersionUID = 4792630537336329581L;

    /** 拖动插件 */
    protected HDragPlugin dragPlugin = new HDragPlugin(this);

    public HBaseDialog(String title) {
        super((Frame) null, title);
        baseFramMouseInit();
    }

    public HBaseDialog(Frame owner, String title, boolean isModel) {
        super(owner, title, isModel);
        baseFramMouseInit();
    }

    public HBaseDialog(Dialog owner, String title, boolean isModel) {
        super(owner, title, isModel);
        baseFramMouseInit();
    }

    /** 初始化方法 */
    private void baseFramMouseInit() {
        // 鼠标点击事件
        addMouseListener(dragPlugin);
        // 鼠标拖动事件
        addMouseMotionListener(dragPlugin);
    }

    /** 显示/隐藏组件 */
    public void toggle() {
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }

    /**
     * @return 返回窗口能否被拖动
     */
    public boolean isCanDrag() {
        return dragPlugin.isCanDrag();
    }

    /**
     * @param isCanDrag
     *            设置窗口能否被拖动
     */
    public void setCanDrag(boolean isCanDrag) {
        dragPlugin.setCanDrag(isCanDrag);
    }

}
