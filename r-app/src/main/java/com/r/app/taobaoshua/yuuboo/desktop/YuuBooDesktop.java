/**
 * 
 */
package com.r.app.taobaoshua.yuuboo.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;

import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.app.taobaoshua.yuuboo.data.DataChangerListener;
import com.r.app.taobaoshua.yuuboo.desktop.tablemodel.PVListTableModel;
import com.r.app.taobaoshua.yuuboo.desktop.tablemodel.PVQuestListTableModel;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.HBaseScrollPane;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.label.HClickLabel;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.exceptions.LogginErrorException;
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
public class YuuBooDesktop extends HBaseDialog implements DataChangerListener {
	private static final long serialVersionUID = 4683581855428200977L;
	private static final int CTRL_STRUT = 5;
	private static final Logger logger = LoggerFactory.getLogger(YuuBooDesktop.class);
	private static final YuuBoo yuuBoo = YuuBoo.getInstance();

	private JButton loginYuuBooButton = new JButton("登陆");
	private JTextField accountTextField = new JTextField(); // 友保账号
	private JTextField accountPasswordTextField = new JTextField(); // 友保妖精账号密码
	private JTextField captchaTextField = new JTextField(); // 验证码
	private JTextField questionTextField = new JTextField(); // 密保问题
	private JTextField answerTextField = new JTextField(); // 密保答案
	private HImagePanel captchaImagePanel = new HImagePanel(new Dimension(70, 20), null); // 验证码图片
	private HClickLabel reacquireCaptchaLabel = new HClickLabel("重新获取", Color.RED, null); // 重新获取验证码

	private JTable pvTable; // pvs列表
	private PVListTableModel pvTableModel; // pvs列表数据源

	private JTable pvQuestTable; // pvs列表
	private PVQuestListTableModel pvQuestTableModel; // pvs列表数据源

	public YuuBooDesktop(Frame owner) {
		super(owner, "优保任务刷子", false);
		logger.info("Init Desktop.........");
		// app.getDataContext().addChangerListener(this);
		initStyle();
		initComponents();
		// initListeners();
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
			yuuBoo.getYuuBooAction().startPVListCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动获取[PV]列表功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

		try {
			yuuBoo.getYuuBooAction().startPVQuestListCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动获取[PV任务]列表功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

		try {
			yuuBoo.getYuuBooAction().startAutoTakeTask();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动[接手PV]功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

		try {
			yuuBoo.getYuuBooAction().startExecCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动[完成淘宝搜索]功能失效,请手动获取同时联系开发者说明情况.", YuuBooDesktop.this, e);
		}

		try {
			yuuBoo.getYuuBooAction().startAutoSaveDatas();
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
		// 登陆区

		// 默认值.临时的
		accountTextField.setText("oky120002");
		accountPasswordTextField.setText("yuyu0619@qq.com");
		questionTextField.setText("我父亲的名字？");
		answerTextField.setText("何雁明");

		// 上-左边
		HBaseBox northBox = HBaseBox.createHorizontalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("登陆")); // 设置箱子组件内边距

		// HBaseBox northLeftBox = HBaseBox.createVerticalBaseBox(); // 账号录入区
		// northLeftBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		// // 设置箱子组件内边距
		// northBox.add(northLeftBox);

		HBaseBox accountBox = HBaseBox.createHorizontalBaseBox();
		accountBox.add(new JLabel("友保账号："));
		accountBox.add(accountTextField);
		northBox.add(accountBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		HBaseBox accountPasswordBox = HBaseBox.createHorizontalBaseBox();
		accountPasswordBox.add(new JLabel("友保密码："));
		accountPasswordBox.add(accountPasswordTextField);
		northBox.add(accountPasswordBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		HBaseBox gameCaptchaTextFieldBox = HBaseBox.createHorizontalBaseBox();
		gameCaptchaTextFieldBox.add(new JLabel("验 证 码："));
		gameCaptchaTextFieldBox.add(captchaTextField);
		northBox.add(gameCaptchaTextFieldBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		HBaseBox questionTextFieldBox = HBaseBox.createHorizontalBaseBox();
		questionTextFieldBox.add(new JLabel("密保问题："));
		questionTextFieldBox.add(questionTextField);
		northBox.add(questionTextFieldBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		HBaseBox answerTextFieldBox = HBaseBox.createHorizontalBaseBox();
		answerTextFieldBox.add(new JLabel("密保答案："));
		answerTextFieldBox.add(answerTextField);
		northBox.add(answerTextFieldBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		// 验证码
		HBaseBox captchaBox = HBaseBox.createHorizontalBaseBox();
		captchaBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // 设置箱子组件内边距
		captchaBox.add(captchaImagePanel);
		captchaBox.add(reacquireCaptchaLabel);
		northBox.add(captchaBox);

		add(northBox, BorderLayout.NORTH);

		// // 下边
		// HBaseBox southBox = HBaseBox.createHorizontalBaseBox();
		// southBox.setBorder(BorderFactory.createEmptyBorder(CTRL_STRUT,
		// CTRL_STRUT, CTRL_STRUT, CTRL_STRUT)); // 设置箱子组件内边距
		// southBox.add(checkBox);
		// southBox.add(HBaseBox.createHorizontalGlue());
		// southBox.add(loginYuuBooButton).requestFocus();
		// southBox.add(HBaseBox.createHorizontalStrut(CTRL_STRUT));
		// southBox.add(exitButton);
		// add(southBox, BorderLayout.SOUTH);

		// PV和PV任务列表
		HBaseBox centerBox = HBaseBox.createVerticalBaseBox();
		pvTableModel = new PVListTableModel(yuuBoo.getYuuBooDataContext());
		pvTable = new JTable(pvTableModel);
		pvTable.setPreferredScrollableViewportSize(new Dimension(600, 150));
		pvTable.setFillsViewportHeight(true);
		pvTable.setDragEnabled(false);
		pvTable.doLayout();
		HBaseBox pvTableBox = HBaseBox.createVerticalBaseBox();
		pvTableBox.setBorder(BorderFactory.createTitledBorder("PV列表"));
		pvTableBox.add(new HBaseScrollPane(pvTable));
		centerBox.add(pvTableBox);
		pvQuestTableModel = new PVQuestListTableModel(yuuBoo.getYuuBooDataContext());
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
					HAlert.showErrorTips("用户名，密码，验证 不能为空", YuuBooDesktop.this);
					loginYuuBooButton.setEnabled(true);
					return;
				}
				String question = questionTextField.getText();
				String answer = answerTextField.getText();
				try {
					yuuBoo.getYuuBooAction().loginYuuBoo(account, accountPassword, captcha, question, answer);
					setVisible(false);
				} catch (LogginErrorException lee) {
					doCaptchaImage();
					if (lee.getErrorMark() == 2) {
						loginYuuBooButton.setText("身份验证");
						captchaTextField.setText("");
					}
					loginYuuBooButton.setEnabled(true);
					HAlert.showErrorTips(lee.getMessage(), YuuBooDesktop.this);
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
					captcha = yuuBoo.getYuuBooAction().getLoginYuuBooCaptchaImage();
				} catch (NetworkIOReadErrorException e) {
					HAlert.showErrorTips(e.getMessage(), YuuBooDesktop.this, e);
				}
				YuuBooDesktop.this.captchaImagePanel.setImage(captcha);
			}
		});
	}
}
