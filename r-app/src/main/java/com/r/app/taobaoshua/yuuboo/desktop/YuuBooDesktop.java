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
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;

import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.app.taobaoshua.yuuboo.data.YuuBooDataChangerListener;
import com.r.app.taobaoshua.yuuboo.desktop.tablemodel.PVListTableModel;
import com.r.app.taobaoshua.yuuboo.desktop.tablemodel.PVQuestListTableModel;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.HBaseScrollPane;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.label.HClickLabel;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.exceptions.LoginErrorException;
import com.r.core.exceptions.SwitchPathException;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * 桌面窗口管理器
 * 
 * @author oky
 * 
 */
public class YuuBooDesktop extends HBaseDialog implements YuuBooDataChangerListener, ActionListener {
	private static final Logger logger = LoggerFactory.getLogger(YuuBooDesktop.class);
	private static final YuuBoo yuuBoo = YuuBoo.getInstance();
	private static final long serialVersionUID = 4683581855428200977L;
	private static final int CTRL_STRUT = 5;
	private static final String COMMOND_LOGIN = "commond_login"; // 命令_登陆

	private JButton loginYuuBooButton = new JButton("登陆");
	private HBaseBox accountBox = null; // 账号区域
	private JTextField accountTextField = new JTextField(); // 友保账号
	private JTextField accountPasswordTextField = new JPasswordField(); // 友保妖精账号密码
	private JTextField captchaTextField = new JTextField(); // 验证码
	private HBaseBox questionBox = null; // 密保区域
	private JTextField questionTextField = new JTextField(); // 密保问题
	private JTextField answerTextField = new JPasswordField(); // 密保答案
	private HImagePanel captchaImagePanel = new HImagePanel(new Dimension(70, 20), null); // 验证码图片
	private HClickLabel reacquireCaptchaLabel = new HClickLabel("重新获取", Color.RED, null); // 重新获取验证码

	private JTable pvTable; // pvs列表
	private PVListTableModel pvTableModel; // pvs列表数据源

	private JTable pvQuestTable; // pvs列表
	private PVQuestListTableModel pvQuestTableModel; // pvs列表数据源

	public YuuBooDesktop(Frame owner) {
		super(owner, "优保任务刷工具", false);
		logger.info("YuuBooDesktop newInstance...................");
		yuuBoo.getDataContext().addChangerListener(this);
		initStyle();
		initComponents();
		initListeners();
	}

	private void initListeners() {
		loginYuuBooButton.addActionListener(this);
		loginYuuBooButton.setActionCommand(COMMOND_LOGIN);

		captchaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					YuuBooDesktop.this.doLogin();
				}
				super.keyPressed(e);
			}
		});

		reacquireCaptchaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				YuuBooDesktop.this.doCaptchaImage();
			}
		});

		captchaImagePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				YuuBooDesktop.this.doCaptchaImage();
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case COMMOND_LOGIN:
			doLogin();
			break;
		}

	}

	@Override
	public void changePVList() {
		pvTable.updateUI();
	}

	@Override
	public void changePVQuestList() {
		pvQuestTable.updateUI();
	}

	/** 启动各种自动执行的命令 */
	public void startCommand() {
		try {
			yuuBoo.getAction().startPVListCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动获取[PV]列表功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

		try {
			yuuBoo.getAction().startPVQuestListCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动获取[PV任务]列表功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

		try {
			yuuBoo.getAction().startAutoTakeTask();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动[接手PV]功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

		try {
			yuuBoo.getAction().startExecCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动[完成淘宝搜索]功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

		try {
			yuuBoo.getAction().startAutoSaveDatas();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动[保存数据]功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

	}

	/** 初始化窗口样式 */
	private void initStyle() {
		setTitle("淘宝刷......");
		setSize(new Dimension(800, 600));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
	}

	/** 初始化控件 */
	private void initComponents() {

		// 默认值.临时的
		accountTextField.setText("oky120002");
		accountPasswordTextField.setText("yuyu0619@qq.com");
		questionTextField.setText("我父亲的名字？");
		answerTextField.setText("何雁明");

		// 登陆区
		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("登陆区")); // 设置箱子组件内边距

		// 账号
		accountBox = HBaseBox.createHorizontalBaseBox();
		accountBox.add(new JLabel("友保账号："));
		accountBox.add(accountTextField);
		accountBox.add(new JLabel("友保密码："));
		accountBox.add(accountPasswordTextField);
		northBox.add(accountBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		// 密保
		questionBox = HBaseBox.createHorizontalBaseBox();
		questionBox.setVisible(false);
		questionBox.add(new JLabel("密保问题："));
		questionBox.add(questionTextField);
		questionBox.add(new JLabel("密保答案："));
		questionBox.add(answerTextField);
		northBox.add(questionBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		// 验证码
		HBaseBox gameCaptchaTextFieldBox = HBaseBox.createHorizontalBaseBox();
		gameCaptchaTextFieldBox.add(new JLabel("验 证 码："));
		gameCaptchaTextFieldBox.add(captchaTextField);
		gameCaptchaTextFieldBox.add(captchaImagePanel);
		gameCaptchaTextFieldBox.add(reacquireCaptchaLabel);
		northBox.add(gameCaptchaTextFieldBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		// 登陆按钮
		northBox.add(loginYuuBooButton);

		add(northBox, BorderLayout.NORTH);

		// PV和PV任务列表
		HBaseBox centerBox = HBaseBox.createVerticalBaseBox();
		pvTableModel = new PVListTableModel(yuuBoo.getDataContext());
		pvTable = new JTable(pvTableModel);
		pvTable.setPreferredScrollableViewportSize(new Dimension(600, 150));
		pvTable.setFillsViewportHeight(true);
		pvTable.setDragEnabled(false);
		pvTable.doLayout();
		HBaseBox pvTableBox = HBaseBox.createVerticalBaseBox();
		pvTableBox.setBorder(BorderFactory.createTitledBorder("PV列表"));
		pvTableBox.add(new HBaseScrollPane(pvTable));
		centerBox.add(pvTableBox);
		pvQuestTableModel = new PVQuestListTableModel(yuuBoo.getDataContext());
		pvQuestTable = new JTable(pvQuestTableModel);
		pvQuestTable.setPreferredScrollableViewportSize(new Dimension(600, 150));
		pvQuestTable.setFillsViewportHeight(true);
		pvQuestTable.setDragEnabled(false);
		pvQuestTable.doLayout();
		HBaseBox pvQuestTableBox = HBaseBox.createVerticalBaseBox();
		pvQuestTableBox.setBorder(BorderFactory.createTitledBorder("PVQuest列表"));
		pvQuestTableBox.add(new HBaseScrollPane(pvQuestTable));
		centerBox.add(pvQuestTableBox);
		add(centerBox, BorderLayout.CENTER);
	}

	/** 登陆 */
	private void doLogin() {
		logger.debug("登陆...........");

		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				loginYuuBooButton.setEnabled(false);
				String account = accountTextField.getText();
				String accountPassword = accountPasswordTextField.getText();
				String captcha = captchaTextField.getText();
				if (StringUtils.isBlank(account) || StringUtils.isBlank(accountPassword) || StringUtils.isBlank(captcha)) {
					HAlert.showError("用户名，密码，验证 不能为空", YuuBooDesktop.this);
					loginYuuBooButton.setEnabled(true);
					return;
				}
				String question = questionTextField.getText();
				String answer = answerTextField.getText();
				try {
					accountTextField.setEnabled(false);
					accountPasswordTextField.setEnabled(false);

					captchaTextField.setEnabled(false);
					captchaImagePanel.setEnabled(false);
					reacquireCaptchaLabel.setEnabled(false);

					questionTextField.setEnabled(false);
					answerTextField.setEnabled(false);

					loginYuuBooButton.setEnabled(false);

					yuuBoo.getAction().loginYuuBoo(account, accountPassword, captcha, question, answer);
					yuuBoo.getDataContext().setAccount(account); // 设置账号
					startCommand();
				} catch (LoginErrorException lee) {
					doCaptchaImage();
					captchaTextField.setText("");
					captchaTextField.setEnabled(true);
					captchaImagePanel.setEnabled(true);
					reacquireCaptchaLabel.setEnabled(true);
					loginYuuBooButton.setEnabled(true);
					switch (lee.getErrorMark()) {
					case 1: // 验证码错误
						if (accountBox.isVisible()) { // 登陆
							accountBox.setVisible(true);
							questionBox.setVisible(false);

							accountTextField.setEnabled(true);
							accountPasswordTextField.setEnabled(true);
							loginYuuBooButton.setText("登陆");
						} else { // 身份验证
							accountBox.setVisible(false);
							questionBox.setVisible(true);

							questionTextField.setEnabled(true);
							answerTextField.setEnabled(true);
							loginYuuBooButton.setText("身份验证");
						}
						break;
					case 2: // 需要身份验证
					case 3: // 安全问题回答错误
						accountBox.setVisible(false);
						questionBox.setVisible(true);

						questionTextField.setEnabled(true);
						answerTextField.setEnabled(true);

						loginYuuBooButton.setText("身份验证");
						break;
					case 4: // 密码错误
						accountBox.setVisible(true);
						questionBox.setVisible(false);

						accountTextField.setEnabled(true);
						accountPasswordTextField.setEnabled(true);
						accountPasswordTextField.setText("");
						loginYuuBooButton.setText("登陆");
						break;
					case 5:
						accountBox.setVisible(true);
						questionBox.setVisible(false);

						accountTextField.setEnabled(false);
						accountTextField.setText("");
						accountPasswordTextField.setEnabled(false);
						accountPasswordTextField.setText("");
						captchaTextField.setEnabled(false);
						captchaImagePanel.setEnabled(false);
						reacquireCaptchaLabel.setEnabled(false);
						loginYuuBooButton.setText("登陆");
						loginYuuBooButton.setEnabled(false);
						break;
					default:
						loginYuuBooButton.setEnabled(false);
						throw new SwitchPathException("未知错误，请联系开发者！");
					}
					HAlert.showError(lee.getMessage(), YuuBooDesktop.this);
				} catch (NetworkIOReadErrorException niree) {
					doCaptchaImage();
					HAlert.showErrorTips(niree.getMessage(), YuuBooDesktop.this, niree);
					loginYuuBooButton.setEnabled(true);
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
					captcha = yuuBoo.getAction().getLoginYuuBooCaptchaImage();
				} catch (NetworkIOReadErrorException e) {
					HAlert.showErrorTips(e.getMessage(), YuuBooDesktop.this, e);
				}
				YuuBooDesktop.this.captchaImagePanel.setImage(captcha);
			}
		});
	}
}
