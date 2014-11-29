/**
 * 描          述:  <展示模块>
 * 修  改   人:  rain
 * 修改时间:  2012-11-12
 * <修改描述:>
 */
package com.r.core.desktop.ctrl;

import javax.swing.JFrame;

import com.r.core.desktop.ctrl.plugin.HDragPlugin;

/**
 * <顶层窗体>
 * 
 * @author rain
 * @version [版本号, 2012-11-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HBaseFrame extends JFrame {
    private static final long serialVersionUID = -7008005707006882286L;

    /** 拖动插件 */
    protected HDragPlugin dragPlugin = new HDragPlugin(this);

    public HBaseFrame() {
        baseFramMouseInit();
    }

    public HBaseFrame(String title) {
        super(title);
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
