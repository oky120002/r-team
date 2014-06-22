/**
 * 
 */
package com.r.app.taobaoshua.yuuboo.desktop;

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
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
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
public class YuuBooCaptchaDialog extends HBaseDialog implements ActionListener {
	private static final long serialVersionUID = 5322552868455278427L;
	private static final Logger logger = LoggerFactory.getLogger(YuuBooDesktop.class);
	private static final String COMMAND_OK = "command_ok";

	private JTextField captchaTextField = new JTextField(); // 验证码
	private HImagePanel captchaImagePanel = new HImagePanel(new Dimension(70, 20), null); // 验证码图片
	private HClickLabel reacquireCaptchaLabel = new HClickLabel("重新获取", Color.RED, null); // 重新获取验证码
	private JButton okYuuBooButton = new JButton("确定");
	private String captcha = null;

	public YuuBooCaptchaDialog() {
		super((Frame) null, "优保验证码", true);
		initStyle();
		initComponents();
		initListeners();
	}

	public String getCaptcha() {
		captchaTextField.setText("");
		doCaptchaImage();
		TaskUtil.sleep(1_000);
		setVisible(true);
		setAlwaysOnTop(true);
		return captcha;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case COMMAND_OK:
			doOk();
			break;
		}
	}

	private void initStyle() {
		setSize(new Dimension(400, 70));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)

		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("登陆区")); // 设置箱子组件内边距
	}

	private void initComponents() {
		// 登陆区
		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("登陆区")); // 设置箱子组件内边距

		// 验证码
		HBaseBox gameCaptchaTextFieldBox = HBaseBox.createHorizontalBaseBox();
		gameCaptchaTextFieldBox.add(new JLabel("验 证 码："));
		gameCaptchaTextFieldBox.add(captchaTextField);
		gameCaptchaTextFieldBox.add(captchaImagePanel);
		gameCaptchaTextFieldBox.add(reacquireCaptchaLabel);
		northBox.add(gameCaptchaTextFieldBox);

		// 登陆按钮
		northBox.add(okYuuBooButton);

		add(northBox, BorderLayout.CENTER);
	}

	private void initListeners() {
		okYuuBooButton.addActionListener(this);
		okYuuBooButton.setActionCommand(COMMAND_OK);

		captchaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					YuuBooCaptchaDialog.this.doOk();
				}
				super.keyPressed(e);
			}
		});

		reacquireCaptchaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				YuuBooCaptchaDialog.this.doCaptchaImage();
			}
		});

		captchaImagePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				YuuBooCaptchaDialog.this.doCaptchaImage();
			}
		});

	}

	private void doOk() {
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				// captchaTextField.setEnabled(false);
				// okYuuBooButton.setEnabled(false);
				captcha = captchaTextField.getText();
				// boolean isok =
				// YuuBoo.getInstance().getYuuBooAction().isCheckCaptcha(captcha);
				// FIXME r-core:taobaoshua 修改判断机制..或者删除这个判断机制
				// if (true) {
				YuuBooCaptchaDialog.this.setVisible(false);
				// } else {
				// YuuBooCaptchaDialog.this.captchaTextField.setText("");
				// YuuBooCaptchaDialog.this.doCaptchaImage();
				// captchaTextField.setEnabled(true);
				// okYuuBooButton.setEnabled(true);
				// }
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
					captcha = YuuBoo.getInstance().getAction().getLoginYuuBooCaptchaImage();
				} catch (NetworkIOReadErrorException e) {
					HAlert.showErrorTips(e.getMessage(), YuuBooCaptchaDialog.this, e);
				}
				YuuBooCaptchaDialog.this.captchaImagePanel.setImage(captcha);
			}
		});
	}

}
