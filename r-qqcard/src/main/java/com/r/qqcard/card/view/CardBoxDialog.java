/**
 * 
 */
package com.r.qqcard.card.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.qqcard.notify.context.NotifyContext;

/**
 * 卡片箱子对话框
 * 
 * @author rain
 *
 */
public class CardBoxDialog extends HBaseDialog {
    private static final long serialVersionUID = 5268486548680474881L;

    /** 消息容器 */
    private NotifyContext notify;

    /** 构造一个卡片箱子 */
    public CardBoxDialog(NotifyContext notify, String title) {
        super((JFrame) null, title, false);
        this.notify = notify;

        // 窗口属性
        this.setSize(480, 600); // 调整窗口大小
        this.setLocationRelativeTo(null); // 全屏居中
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); // 关闭对话框,则隐藏窗口
        this.setResizable(false);// 不能调整对话框大小
        this.setLayout(new BorderLayout()); // 设置页面面板布局类型
    }

}
