/**
 * 
 */
package com.r.core.desktop.ctrl;

import javax.swing.JWindow;

import com.r.core.desktop.ctrl.plugin.HDragPlugin;
import com.r.core.desktop.eventlistener.move.MoveListener;

/**
 * @author Administrator
 * 
 */
public class HBaseWindow extends JWindow {
    private static final long serialVersionUID = 3807980269757679678L;

    /** 拖动插件 */
    protected HDragPlugin dragPlugin = new HDragPlugin(this);

    public HBaseWindow() {
        super();
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

    /** 添加移动事件 */
    public void addMoveListener(MoveListener moveListener) {
        this.dragPlugin.addMoveListener(moveListener);
    }

}
