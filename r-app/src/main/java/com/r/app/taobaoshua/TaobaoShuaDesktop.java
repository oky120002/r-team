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
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 登陆窗口
 * 
 * @author Administrator
 * 
 */
public class TaobaoShuaDesktop extends HBaseFrame implements ActionListener {
	private static final long serialVersionUID = -957767869361688549L;
	private static final Logger logger = LoggerFactory.getLogger(TaobaoShuaDesktop.class);
	private static final int CTRL_STRUT = 5; // 控件之间的间隔
	private static final String COMMAND_EXIT = "command_exit"; // 命令_退出
	private static final String COMMAND_AUTOPROXY = "command_autoproxy"; // 命令_自动代理
	private static final String COMMAND_YUUBOO = "command_yuuboo"; // 进入友保系统
	private static final TaobaoShuaApp app = TaobaoShuaApp.getInstance();

	private JCheckBox checkBox = new JCheckBox("启用代理");
	private JButton yuuBooButton = new JButton("友保");
	private JButton exitButton = new JButton("退出");

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
			YuuBoo.getInstance().start();
			break;
		}
	}

	private void initStyle() {
		setSize(new Dimension(350, 220));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initComponents() {
		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("功能区")); // 设置箱子组件内边距

		northBox.add(this.yuuBooButton);

		add(northBox, BorderLayout.NORTH);
	}

	/** 初始化窗口中的事件 */
	private void initListeners() {
		yuuBooButton.addActionListener(this);
		yuuBooButton.setActionCommand(COMMAND_YUUBOO);
	}

	// /** 启动自动代理 */
	// private void proxy() {
	// if (checkBox.isSelected()) {
	// app.setProxy(HttpProxy.newInstance(true, java.net.Proxy.Type.HTTP,
	// "122.96.59.99", 82));
	// } else {
	// app.setProxy(null);
	// }
	// }
}
