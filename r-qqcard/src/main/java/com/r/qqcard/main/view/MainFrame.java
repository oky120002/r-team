/**
 * 
 */
package com.r.qqcard.main.view;

import java.awt.BorderLayout;

import javax.swing.WindowConstants;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.impl.panle.HInfoPanel;

/**
 * 主面板
 * 
 * @author rain
 *
 */
public class MainFrame extends HBaseFrame {
    private static final long serialVersionUID = -1173373452689687625L;

    /** 日志面板 */
    private HInfoPanel logPanel = new HInfoPanel();

    /**
     * 构造一个拥有一个标题的主面板
     * 
     * @param title
     *            标题
     */
    public MainFrame(String title) {
        super(title);

        this.setSize(600, 480); // 调整窗口大小
        this.setLocationRelativeTo(null); // 全屏居中
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); // 关闭对话框,则隐藏窗口
        this.setResizable(false);// 不能调整对话框大小
        this.setLayout(new BorderLayout()); // 设置页面面板布局类型

        HBaseBox box = HBaseBox.createVerticalBaseBox();
        add(box, BorderLayout.CENTER);

        box.add(logPanel);
    }

    /** 打印日志信息 */
    public void printlnInfo(String message) {
        this.logPanel.printlnInfo(message);
    }

}
