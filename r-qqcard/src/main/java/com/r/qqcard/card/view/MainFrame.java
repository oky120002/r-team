/**
 * 
 */
package com.r.qqcard.card.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.impl.panle.HInfoPanel;
import com.r.core.util.StrUtil;
import com.r.qqcard.notify.context.NotifyContext;
import com.r.qqcard.notify.handler.Event;

/**
 * 主窗口
 * 
 * @author rain
 *
 */
public class MainFrame extends HBaseFrame implements ActionListener {
    private static final long serialVersionUID = -1173373452689687625L;

    /** 消息容器 */
    private NotifyContext notify;

    /** 日志面板 */
    private HInfoPanel logPanel = new HInfoPanel();
    /** 昵称 */
    private JLabel usernameLabel = new JLabel("昵称:null\r");
    /** 金币 */
    private JLabel goldLabel = new JLabel("金币:-1\r");
    /** 魔法值 */
    private JLabel manaLabel = new JLabel("魔法值:-1\r");
    /** 等级 */
    private JLabel levelLabel = new JLabel("等级:-1\r");
    /** 经验值 */
    private JLabel expLabel = new JLabel("经验值:-1/-1\r");
    /** 启动自动炼卡按钮 */
    private JButton startAutoButton = new JButton("启动自动炼卡");
    /** 显示储藏箱按钮 */
    private JButton storeBoxButton = new JButton("储藏箱");

    /** 构造一个拥有一个标题的窗口 */
    public MainFrame(NotifyContext notify, String title) {
        super(title);
        this.notify = notify;

        // 窗口属性
        this.setSize(600, 480); // 调整窗口大小
        this.setLocationRelativeTo(null); // 全屏居中
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); // 关闭对话框,则隐藏窗口
        this.setResizable(false);// 不能调整对话框大小
        this.setLayout(new BorderLayout()); // 设置页面面板布局类型

        // 窗口子控件属性
        this.storeBoxButton.setEnabled(false);
        this.storeBoxButton.addActionListener(this);
        this.startAutoButton.setEnabled(false);
        this.startAutoButton.addActionListener(this);

        // 顶部账户信息
        HBaseBox boxTop = HBaseBox.createHorizontalBaseBox();
        add(boxTop, BorderLayout.NORTH);
        boxTop.adds(HBaseBox.EmptyHorizontal, usernameLabel, goldLabel, manaLabel, levelLabel, expLabel);

        // 中部日志
        HBaseBox box = HBaseBox.createVerticalBaseBox();
        add(box, BorderLayout.CENTER);
        box.add(logPanel);

        // 底部功能按钮
        HBaseBox boxBottom = HBaseBox.createHorizontalBaseBox();
        add(boxBottom, BorderLayout.SOUTH);
        boxBottom.adds(startAutoButton, HBaseBox.EmptyHorizontal, storeBoxButton);
    }

    /** 打印日志信息 */
    public void printlnInfo(String message) {
        this.logPanel.printlnInfo(message);
    }

    /** 启动游戏 */
    public void startupGame() {
        this.storeBoxButton.setEnabled(true);
        this.startAutoButton.setEnabled(true);
    }

    /** 设置昵称 */
    public void setNickName(String nickName) {
        this.usernameLabel.setText(StrUtil.formart("昵称:{}\t", nickName));
    }

    /** 设置金币 */
    public void setGold(String gold) {
        this.goldLabel.setText(StrUtil.formart("金币:{}\t", gold));
    }

    /** 设置魔法值 */
    public void setMana(String mana) {
        this.manaLabel.setText(StrUtil.formart("魔法值:{}\t", mana));
    }

    /** 设置等级 */
    public void setLevel(String level) {
        this.levelLabel.setText(StrUtil.formart("等级:{}\t", level));
    }

    /** 设置经验值 */
    public void setExp(String exp, String expLevel) {
        this.expLabel.setText(StrUtil.formart("经验值:{}/{}", exp, expLevel));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(this.storeBoxButton)) {
            notify.notifyEvent(Event.box$显示卡片箱子对话框);
        }
        if (source.equals(this.startAutoButton)) {
            notify.notifyEvent(Event.core$启动自动炼卡);
        }
    }
}
