/**
 * 
 */
package com.r.app.taobaoshua;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.core.CoreUtil;
import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * 登陆窗口
 * 
 * @author Administrator
 * 
 */
public class TaobaoShuaDesktop extends HBaseFrame implements ActionListener {
	private static final long serialVersionUID = -957767869361688549L;
	private static final Logger logger = LoggerFactory.getLogger(TaobaoShuaDesktop.class);
	private static final String COMMAND_EXIT = "command_exit"; // 命令_退出
	private static final String COMMAND_AUTOPROXY = "command_autoproxy"; // 命令_自动代理
	private static final String COMMAND_YUUBOO = "command_yuuboo"; // 进入友保系统
	private static final String COMMAND_BLUESKY = "command_bluesky"; // 进入蓝天数据平台分析系统

	private JLabel ipLabel = new JLabel();
	private JButton yuuBooButton = new JButton("友保");
	private JButton blueskyButton = new JButton("蓝天");

	public TaobaoShuaDesktop(String title) {
		super(title);
		logger.info("MainDesktop newInstance .............");
		initStyle();
		initComponents();
		initListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		switch (actionCommand) {
		case COMMAND_EXIT: // 退出
			System.exit(0);
			break;
		case COMMAND_AUTOPROXY: // 自动代理
			break;
		case COMMAND_YUUBOO: // 友保
			setVisible(false);
			YuuBoo.getInstance().startup();
			break;
		case COMMAND_BLUESKY: // 蓝天
			setVisible(false);
			BlueSky.getInstance().startup();
			break;
		}
	}

	private void initStyle() {
		setSize(new Dimension(350, 220));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
		try {
			setIconImage(CoreUtil.getDefaultIco());
		} catch (Exception e) {
		}
	}

	private void initComponents() {
		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("功能区")); // 设置箱子组件内边距

		northBox.add(this.yuuBooButton);
		northBox.add(HBaseBox.createHorizontalStrut(50));
		northBox.add(this.blueskyButton);

		add(northBox, BorderLayout.CENTER);

		HBaseBox southBox = HBaseBox.createHorizontalBaseBox();
		southBox.setBorder(BorderFactory.createTitledBorder("")); // 设置箱子组件内边距
		southBox.add(new JLabel("外网IP: "));
		southBox.add(this.ipLabel);

		TaskUtil.executeTask(new Runnable() {

			@Override
			public void run() {
				ipLabel.setText(TaobaoShuaApp.getInstance().getCore().getAction().getNetWorkIp());
			}
		});
		add(southBox, BorderLayout.SOUTH);
	}

	/** 初始化窗口中的事件 */
	private void initListeners() {
		yuuBooButton.addActionListener(this);
		yuuBooButton.setActionCommand(COMMAND_YUUBOO);
		blueskyButton.addActionListener(this);
		blueskyButton.setActionCommand(COMMAND_BLUESKY);
	}
}

// 155 S 150~154
// 160 M 153~158
// 165 L 158~164
// 170 XL 162~167
// 175 XXL 165~170
// 180 XXXL 168~175
// 所有尺码根据身高和体重，胸围等有上下浮动.

