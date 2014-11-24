/**
 * 
 */
package com.r.core.desktop.ctrl.impl.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel.ShowMode;

/**
 * 图片对话框
 * 
 * @author rain
 *
 */
public class HImageDialog extends HBaseDialog {
    private static final long serialVersionUID = -3185009893725024260L;

    /** 图片面板 */
    private HImagePanel imagePanel = new HImagePanel();

    /**
     * @param title
     */
    public HImageDialog() {
        super((JFrame) null, "图片对话框", true);
        initStyle();
        initComponent();
    }

    /**
     * 设置图片
     * 
     * @param image
     *            图片
     * @param mode
     *            图片拉伸方式
     */
    public void setImage(Image image, ShowMode mode) {
        imagePanel.setShowMode(mode);
        imagePanel.setImage(image);
    }

    /** 设置窗体样式 */
    private void initStyle() {
        // 设置窗口大小
        this.setSize(new Dimension(600, 400));

        // 让窗体居中显示
        this.setLocationRelativeTo(null);

        // 点击关闭按钮,隐藏窗口
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /** 设置窗体组件 */
    private void initComponent() {
        // 设置页面面板布局类型
        setLayout(new BorderLayout());

        // 初始化信息面板
        add(imagePanel, BorderLayout.CENTER);
    }

}
