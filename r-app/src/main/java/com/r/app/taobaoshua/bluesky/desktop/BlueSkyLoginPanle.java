/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

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
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.label.HClickLabel;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * @author oky
 * 
 */
public class BlueSkyLoginPanle extends HBasePanel implements ActionListener {
	private static final long serialVersionUID = 284808707142803494L;
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyLoginPanle.class);
	private static final BlueSky blueSky = BlueSky.getInstance();
	private static final String COMMAND_LOGIN = "command_login"; // 命令_登陆
	private static final String COMMAND_SKIP = "command_skip"; // 命令_跳过

	private BlueSkyLoginPanleListener listener;

	private JButton loginButton = new JButton("登陆");
	private JButton skipButton = new JButton("跳过");
	private JTextField accountTextField = new JTextField(); // 友保账号
	private JTextField accountPasswordTextField = new JPasswordField(); // 友保妖精账号密码
	private JTextField captchaTextField = new JTextField(); // 验证码
	private JTextField questionTextField = new JTextField(); // 密保问题
	private JTextField answerTextField = new JPasswordField(); // 密保答案
	private HImagePanel captchaImagePanel = new HImagePanel(new Dimension(70, 20), null); // 验证码图片
	private HClickLabel reacquireCaptchaLabel = new HClickLabel("重新获取", Color.RED, null); // 重新获取验证码

	public BlueSkyLoginPanle(BlueSkyLoginPanleListener listener) {
		logger.debug("BlueSkyLoginPanle newInstance..........");
		this.listener = listener;
		initStyle();
		initComponents();
		initListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case COMMAND_LOGIN:
			doLogin();
			break;
		case COMMAND_SKIP:
			if (this.listener != null) {
				this.listener.loginSkip();
			}
			break;
		}
	}

	protected void doCaptchaImage() {
		logger.debug("蓝天数据平台获得验证码图片.......");
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				Image captcha = null;
				try {
					captcha = blueSky.getService().getLoginBlueSkyCaptchaImage();
				} catch (NetworkIOReadErrorException e) {
					HAlert.showErrorTips(e.getMessage(), BlueSkyLoginPanle.this, e);
				}
				BlueSkyLoginPanle.this.captchaImagePanel.setImage(captcha);
			}
		});
	}

	protected void doLogin() {

		logger.debug("登陆...........");

		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				loginButton.setEnabled(false);
				String account = accountTextField.getText();
				String accountPassword = accountPasswordTextField.getText();
				String captcha = captchaTextField.getText();
				String question = questionTextField.getText();
				String answer = answerTextField.getText();
				if (StringUtils.isBlank(question) || StringUtils.isBlank(question) || StringUtils.isBlank(account) || StringUtils.isBlank(accountPassword) || StringUtils.isBlank(captcha)) {
					HAlert.showErrorTips("用户名，密码，验证，密保问题，密保答案 不能为空", BlueSkyLoginPanle.this);
					loginButton.setEnabled(true);
					return;
				}

				try {
					accountTextField.setEnabled(false);
					accountPasswordTextField.setEnabled(false);

					captchaTextField.setEnabled(false);
					captchaImagePanel.setEnabled(false);
					reacquireCaptchaLabel.setEnabled(false);

					questionTextField.setEnabled(false);
					answerTextField.setEnabled(false);

					loginButton.setEnabled(false);

					blueSky.getService().login(account, accountPassword, captcha, question, answer);
					BlueSkyLoginPanle.this.listener.loginFinsh();
				} catch (Exception niree) {
					logger.error("登陆错误", niree);
				}
			}
		});
	}

	private void initStyle() {

	}

	private void initComponents() {

		// 默认值.临时的
		accountTextField.setText("oky120002");
		accountPasswordTextField.setText("lian1990");
		questionTextField.setText("我中学校名全称是什么？");
		answerTextField.setText("杨家坪中学");

		// 登陆区
		HBaseBox centerBox = HBaseBox.createVerticalBaseBox();
		centerBox.setBorder(BorderFactory.createTitledBorder("登陆")); // 设置箱子组件内边距

		// 账号
		centerBox.add(new JLabel("账号："));
		centerBox.add(accountTextField);
		centerBox.add(new JLabel("密码："));
		centerBox.add(accountPasswordTextField);

		// 密保
		centerBox.add(new JLabel("密保问题："));
		centerBox.add(questionTextField);
		centerBox.add(new JLabel("密保答案："));
		centerBox.add(answerTextField);

		// 验证码
		centerBox.add(new JLabel("验 证 码："));
		centerBox.add(captchaTextField);
		centerBox.add(captchaImagePanel);
		centerBox.add(reacquireCaptchaLabel);

		// 登陆按钮
		HBaseBox buttonBox = HBaseBox.createHorizontalBaseBox();
		buttonBox.add(skipButton);
		buttonBox.add(HBaseBox.createHorizontalStrut(5));
		buttonBox.add(loginButton);
		centerBox.add(buttonBox);

		add(centerBox, BorderLayout.NORTH);
	}

	private void initListeners() {
		loginButton.addActionListener(this);
		loginButton.setActionCommand(COMMAND_LOGIN);
		skipButton.addActionListener(this);
		skipButton.setActionCommand(COMMAND_SKIP);

		captchaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					BlueSkyLoginPanle.this.doLogin();
				}
				super.keyPressed(e);
			}
		});

		reacquireCaptchaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				BlueSkyLoginPanle.this.doCaptchaImage();
			}
		});

		captchaImagePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				BlueSkyLoginPanle.this.doCaptchaImage();
			}
		});

	}

}
