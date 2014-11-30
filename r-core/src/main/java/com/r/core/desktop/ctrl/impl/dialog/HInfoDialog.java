/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2012-12-28
 * <修改描述:>
 */
package com.r.core.desktop.ctrl.impl.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.impl.panle.HInfoPanel;

/**
 * 信息对话框
 * 
 * @author rain
 * @version [版本号, 2012-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HInfoDialog extends HBaseDialog {
    private static final long serialVersionUID = -6294574984222305510L;

    /** 中部面板 */
    private HInfoPanel infoPanel = null;

    public HInfoDialog(String title) {
        super(title);
        initStyle();
        initComponent();
    }

    public HInfoDialog(JFrame frame, String title, boolean isModel) {
        super(frame, title, isModel);
        initStyle();
        initComponent();
    }

    public HInfoDialog(JDialog dialog, String title, boolean isModel) {
        super(dialog, title, isModel);
        initStyle();
        initComponent();
    }

    /** 打印信息 */
    public void printlnInfo(String message) {
        infoPanel.printlnInfo(message);
    }

    /** 设置窗体样式 */
    private void initStyle() {
        // 设置窗口大小
        this.setSize(new Dimension(600, 400));

        // 让窗体居中显示
        this.setLocationRelativeTo(null);

        // 点击关闭按钮,隐藏窗口
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /** 设置窗体组件 */
    private void initComponent() {
        // 设置页面面板布局类型
        setLayout(new BorderLayout());

        // 初始化信息面板
        infoPanel = new HInfoPanel();
        add(infoPanel, BorderLayout.CENTER);
    }
}
