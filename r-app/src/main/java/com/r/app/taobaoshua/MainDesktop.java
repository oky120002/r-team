/**
 * 
 */
package com.r.app.taobaoshua;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.app.taobaoshua.yuuboo.desktop.YuuBooDesktop;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.label.HClickLabel;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.exceptions.LogginErrorException;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * 登陆窗口
 * 
 * @author Administrator
 * 
 */
public class MainDesktop extends HBaseFrame implements ActionListener {
	private static final long serialVersionUID = -957767869361688549L;
	private static final Logger logger = LoggerFactory.getLogger(MainDesktop.class);
	private static final int CTRL_STRUT = 5; // 控件之间的间隔
	private static final String COMMAND_EXIT = "command_exit"; // 命令_退出
	private static final String COMMAND_AUTOPROXY = "command_autoproxy"; // 命令_自动代理
	private static final String COMMAND_YUUBOO = "command_yuuboo"; // 进入友保系统
	private static final TaobaoShuaApp app = TaobaoShuaApp.getInstance();

	private JCheckBox checkBox = new JCheckBox("启用代理");
	private JButton yuuBooButton = new JButton("友保");
	private JButton exitButton = new JButton("退出");

	private YuuBooDesktop yuuBooDesktop = null;

	// YuuBoo

	public MainDesktop(String title) {
		super(title);
		initStyle();
		initComponents();
		initListeners();
		yuuBooDesktop = new YuuBooDesktop(this);
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
		case COMMAND_YUUBOO:
			YuuBoo.getInstance().start();
			break;
		}
	}

	private void initStyle() {
		setSize(new Dimension(350, 220));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
	}

	private void initComponents() {
		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("登陆")); // 设置箱子组件内边距

		northBox.add(this.yuuBooButton);

		add(northBox, BorderLayout.NORTH);
	}

	/** 初始化窗口中的事件 */
	private void initListeners() {
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
