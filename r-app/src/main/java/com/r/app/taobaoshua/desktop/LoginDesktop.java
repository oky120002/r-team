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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
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
public class LoginDesktop extends HBaseDialog implements ActionListener {
	private static final long serialVersionUID = -957767869361688549L;
	private static final Logger logger = LoggerFactory.getLogger(LoginDesktop.class);
	private static final int CTRL_STRUT = 5; // 控件之间的间隔
	private static final String COMMAND_LOGIN = "command_login"; // 命令_登陆
	private static final String COMMAND_EXIT = "command_exit"; // 命令_退出
	private static final String COMMAND_AUTOPROXY = "command_autoproxy"; // 命令_自动代理
	private static final TaobaoShuaApp app = TaobaoShuaApp.getInstance();

	private JTextField accountTextField = new JTextField(); // 友保账号
	private JTextField accountPasswordTextField = new JTextField(); // 友保妖精账号密码
	private JTextField captchaTextField = new JTextField(); // 验证码
	private JTextField questionTextField = new JTextField(); // 密保问题
	private JTextField answerTextField = new JTextField(); // 密保答案
	private HImagePanel captchaImagePanel = new HImagePanel(new Dimension(70, 20), null); // 验证码图片
	private HClickLabel reacquireCaptchaLabel = new HClickLabel("重新获取", Color.RED, null); // 重新获取验证码
	private JCheckBox checkBox = new JCheckBox("启用代理");
	private JList proxyList = new JList();
	private JButton loginButton = new JButton("登陆");
	private JButton exitButton = new JButton("取消");

	public LoginDesktop(Frame owner, String title, boolean isModel) {
		super(owner, title, isModel);
		initStyle();
		initComponents();
		initListeners();
		// doCaptchaImage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		switch (actionCommand) {
		case COMMAND_LOGIN: // 登陆
			doLogin();
			break;
		case COMMAND_EXIT: // 退出
			System.exit(0);
			break;
		case COMMAND_AUTOPROXY: // 自动代理
			proxy();
			break;
		}
	}

	private void initStyle() {
		setSize(new Dimension(350, 220));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
		// setUndecorated(true); // 隐藏关闭按钮的方法

	}

	private void initComponents() {
		// 默认值.临时的
		accountTextField.setText("oky120002");
		accountPasswordTextField.setText("yuyu0619@qq.com");
		questionTextField.setText("我父亲的名字？");
		answerTextField.setText("何雁明");

		// 上-左边
		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("登陆")); // 设置箱子组件内边距

		HBaseBox northLeftBox = HBaseBox.createVerticalBaseBox(); // 账号录入区
		northLeftBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // 设置箱子组件内边距
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

		HBaseBox questionTextFieldBox = HBaseBox.createHorizontalBaseBox();
		questionTextFieldBox.add(new JLabel("密保问题："));
		questionTextFieldBox.add(questionTextField);
		northLeftBox.add(questionTextFieldBox);
		northLeftBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		HBaseBox answerTextFieldBox = HBaseBox.createHorizontalBaseBox();
		answerTextFieldBox.add(new JLabel("密保答案："));
		answerTextFieldBox.add(answerTextField);
		northLeftBox.add(answerTextFieldBox);
		northLeftBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		// 上-右边
		HBaseBox northRightBox = HBaseBox.createVerticalBaseBox();
		northRightBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // 设置箱子组件内边距
		northBox.add(northRightBox);

		// 验证码
		HBaseBox captchaBox = HBaseBox.createHorizontalBaseBox();
		captchaBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // 设置箱子组件内边距
		captchaBox.add(captchaImagePanel);
		captchaBox.add(reacquireCaptchaLabel);
		northRightBox.add(captchaBox);
		northRightBox.add(HBaseBox.createVerticalStrut(100));

		add(northBox, BorderLayout.CENTER);

		// 下边
		HBaseBox southBox = HBaseBox.createHorizontalBaseBox();
		southBox.setBorder(BorderFactory.createEmptyBorder(CTRL_STRUT, CTRL_STRUT, CTRL_STRUT, CTRL_STRUT)); // 设置箱子组件内边距
		southBox.add(checkBox);
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
		checkBox.setActionCommand(COMMAND_AUTOPROXY);
		checkBox.addActionListener(this);

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
				if (StringUtils.isBlank(account) || StringUtils.isBlank(accountPassword) || StringUtils.isBlank(captcha)) {
					HAlert.showErrorTips("用户名，密码，验证 不能为空", LoginDesktop.this);
					loginButton.setEnabled(true);
					return;
				}
				String question = questionTextField.getText();
				String answer = answerTextField.getText();
				try {
					app.getAction().login(account, accountPassword, captcha, question, answer);
					setVisible(false);
				} catch (LogginErrorException lee) {
					doCaptchaImage();
					if (lee.getErrorMark() == 2) {
						loginButton.setText("身份验证");
						captchaTextField.setText("");
					}
					loginButton.setEnabled(true);
					HAlert.showErrorTips(lee.getMessage(), LoginDesktop.this);
				} catch (NetworkIOReadErrorException niree) {
					doCaptchaImage();
					HAlert.showErrorTips(niree.getMessage(), LoginDesktop.this, niree);
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
				}
				LoginDesktop.this.captchaImagePanel.setImage(captcha);
			}
		});
	}

	/** 启动自动代理 */
	private void proxy() {
		if (checkBox.isSelected()) {
			app.setProxy(HttpProxy.newInstance(true, java.net.Proxy.Type.HTTP, "122.96.59.102", 80));
		} else {
			app.setProxy(null);
		}
	}
}
