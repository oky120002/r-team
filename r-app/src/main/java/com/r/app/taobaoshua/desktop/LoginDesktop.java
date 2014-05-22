/**
 * 
 */
package com.r.app.taobaoshua.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.label.HClickLabel;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.exceptions.CaptchaErrorException;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * 登陆窗口
 * 
 * @author Administrator
 * 
 */
public class LoginDesktop extends HBaseDialog implements ActionListener {
	private static final long serialVersionUID = -957767869361688549L;
	private static final Logger logger = LoggerFactory.getLogger(LoginDesktop.class);
	private static final int CTRL_STRUT = 5; // 控件之间的间隔
	private static final String COMMAND_LOGIN = "command_login"; // 命令_登陆
	private static final String COMMAND_EXIT = "command_exit"; // 命令_退出
	private static final TaobaoShuaApp app = TaobaoShuaApp.getInstance();

	private JTextField accountTextField = new JTextField(); // 友保账号
	private JTextField accountPasswordTextField = new JTextField(); // 友保妖精账号密码
	private JTextField captchaTextField = new JTextField(); // 验证码
	private HImagePanel captchaImagePanel = new HImagePanel(new Dimension(70, 20), null); // 验证码图片
	private HClickLabel reacquireCaptchaLabel = new HClickLabel("点击重新获取验证码", Color.RED, null); // 重新获取验证码
	private JButton loginButton = new JButton("登陆");
	private JButton exitButton = new JButton("取消");

	public LoginDesktop(Frame owner, String title, boolean isModel) {
		super(owner, title, isModel);
		initStyle();
		initComponents();
		initListeners();
		doCaptchaImage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if (COMMAND_LOGIN.equals(actionCommand)) { // 登陆
			doLogin();
			return;
		}

		if (COMMAND_EXIT.equals(actionCommand)) { // 退出
			System.exit(0);
			return;
		}
	}

	private void initStyle() {
		setSize(new Dimension(350, 160));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
		// setUndecorated(true); // 隐藏关闭按钮的方法

	}

	private void initComponents() {
		// 默认值.临时的
		accountTextField.setText("oky120002");
		accountPasswordTextField.setText("yuyu0619@qq.com");

		// 数据录入区
		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("登陆")); // 设置箱子组件内边距

		HBaseBox northLeftBox = HBaseBox.createVerticalBaseBox(); // 账号录入区
		northLeftBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15)); // 设置箱子组件内边距
		northBox.add(northLeftBox);

		HBaseBox accountBox = HBaseBox.createHorizontalBaseBox();
		accountBox.add(new JLabel("友保账号："));
		accountBox.add(accountTextField);
		northLeftBox.add(accountBox);
		northLeftBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		HBaseBox accountPasswordBox = HBaseBox.createHorizontalBaseBox();
		accountPasswordBox.add(new JLabel("友保密码："));
		accountPasswordBox.add(accountPasswordTextField);
		northLeftBox.add(accountPasswordBox);
		northLeftBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		HBaseBox gameCaptchaTextFieldBox = HBaseBox.createHorizontalBaseBox();
		gameCaptchaTextFieldBox.add(new JLabel("验 证 码："));
		gameCaptchaTextFieldBox.add(captchaTextField);
		northLeftBox.add(gameCaptchaTextFieldBox);
		northLeftBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		HBaseBox northRightBox = HBaseBox.createVerticalBaseBox();
		northRightBox.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); // 设置箱子组件内边距
		northBox.add(northRightBox);

		northRightBox.add(HBaseBox.createHorizontalStrut(20));
		northRightBox.add(captchaImagePanel);
		northRightBox.add(reacquireCaptchaLabel);

		add(northBox, BorderLayout.CENTER);

		// ////////////////////////////
		// 命令按钮区
		// 按钮
		HBaseBox southBox = HBaseBox.createHorizontalBaseBox();
		southBox.setBorder(BorderFactory.createEmptyBorder(CTRL_STRUT, CTRL_STRUT, CTRL_STRUT, CTRL_STRUT)); // 设置箱子组件内边距
		southBox.add(HBaseBox.createHorizontalGlue());
		southBox.add(loginButton).requestFocus();
		southBox.add(HBaseBox.createHorizontalStrut(CTRL_STRUT));
		southBox.add(exitButton);
		add(southBox, BorderLayout.SOUTH);

	}

	/** 初始化窗口中的事件 */
	private void initListeners() {
		loginButton.setActionCommand(COMMAND_LOGIN);
		loginButton.addActionListener(this);
		exitButton.setActionCommand(COMMAND_EXIT);
		exitButton.addActionListener(this);

		captchaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoginDesktop.this.doLogin();
				}
				super.keyPressed(e);
			}
		});

		reacquireCaptchaLabel.setClickAdapter(new Runnable() {
			@Override
			public void run() {
				doCaptchaImage();
			}

		}, true);

		captchaImagePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doCaptchaImage();
			}
		});
	}

	/** 登陆 */
	private void doLogin() {
		logger.debug("登陆.....");

		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				loginButton.setEnabled(false);
				String account = accountTextField.getText();
				String accountPassword = accountPasswordTextField.getText();
				String captcha = captchaTextField.getText();
				try {
					app.getAction().login(account, accountPassword, captcha);
					setVisible(false);
				} catch (CaptchaErrorException e) {
					doCaptchaImage();
					HAlert.showErrorTips(e.getMessage(), LoginDesktop.this, e);
					loginButton.setEnabled(true);
				} catch (IOException e) {
					doCaptchaImage();
					HAlert.showErrorTips(e.getMessage(), LoginDesktop.this, e);
					loginButton.setEnabled(true);

				}
			}
		});

	}

	/** 获取友保验证码 */
	private void doCaptchaImage() {
		logger.debug("获得验证码图片.......");
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				Image captcha = null;

				try {
					captcha = app.getAction().getLoginCaptchaImage();
				} catch (NetworkIOReadErrorException e) {
					HAlert.showErrorTips(e.getMessage(), LoginDesktop.this, e);
				} catch (IOException e) {
					HAlert.showErrorTips(e.getMessage(), LoginDesktop.this, e);
				}
				LoginDesktop.this.captchaImagePanel.setImage(captcha);
			}
		});
	}
}
