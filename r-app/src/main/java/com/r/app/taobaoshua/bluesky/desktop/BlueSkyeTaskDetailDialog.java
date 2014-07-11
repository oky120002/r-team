/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.BuyAccount;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.app.taobaoshua.core.CoreUtil;
import com.r.core.callback.SuccessAndFailureCallBack;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

// 已经支付
// /task/TaskJieShou.asp?Tid=0&Status=3
// Referer: http://www2.88sxy.com/task/TaskJieShou.asp?Action=Pay&ID=659090

/**
 * @author Administrator
 * 
 */
public class BlueSkyeTaskDetailDialog extends HBaseDialog implements ActionListener {
	private static final long serialVersionUID = -7140304585521247842L;
	private static final String GROPU = BlueSkyeTaskDetailDialog.class.getName();
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyeTaskDetailDialog.class);
	private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
	private static final BlueSky blueSky = BlueSky.getInstance();
	private static final String COMMAND_ACCEPT_TASK = "command_accept_task"; // 命令_接手任务
	private static final String COMMAND_PAICHU_TASK = "COMMAND_PAICHU_TASK"; // 命令_排除此任务.不再接手
	private static final String COMMAND_DISCARD_TASK = "command_discard_task"; // 命令_放弃任务
	private static final String COMMAND_DO_GOOD_PRAISE = "command_do_good_praise"; // 命令_已经好评
	private static final String COMMAND_DO_GOOD_DEGREE = "command_do_good_degree"; // 命令_满意度评价
	private static final String COMMAND_BINDING_BUYACCOUNT = "command_binding_buyaccount"; // 命令_绑定买号
	private static final String COMMAND_SELECT_BUYACCOUNT = "command_select_buyaccount"; // 选定绑定的买号
	private Task task = new Task(); // 任务

	private JLabel numberLabel = new JLabel(); // 任务编号
	private JLabel taskTypeLabel = new JLabel(); // 任务类型
	private JLabel shopkeeperLabel = new JLabel(); // 掌柜名称
	private JLabel itemTitleLabel = new JLabel(); // 宝贝标题
	private JLabel paymentTypeLabel = new JLabel(); // 支付方式
	private JLabel securityPriceLabel = new JLabel(); // 担保金额
	private JLabel publishingPointLabel = new JLabel(); // 发布点数
	private JLabel isSincerityLabel = new JLabel(); // 商保
	private JLabel isIDCardLabel = new JLabel(); // 实名
	private JLabel isSearchLabel = new JLabel(); // 搜搜
	private JLabel isCollectLabel = new JLabel(); // 收藏
	private JLabel isWangWangLabel = new JLabel(); // 旺旺
	private JLabel isReviewLabel = new JLabel(); // 审核
	private JLabel isQQLabel = new JLabel(); // QQ
	private JLabel isUpdatePriceLabel = new JLabel(); // 是否改价
	private JLabel isAddrLabel = new JLabel(); // 是否改价
	private JLabel isBaoGuoLabel = new JLabel(); // 是否改价
	private JLabel taskStatusLabel = new JLabel(); // 状态
	private JLabel taskerAccountLabel = new JLabel(); // 接收人账号
	private JTextField praiseTextField = new JTextField(); // 好评内容
	private JLabel buyAccountCreditLabel = new JLabel(); // 买号信誉

	private JButton acceptTaskButton = new JButton("接手任务"); // 接手任务
	private JButton discardTaskButton = new JButton("放弃任务");// 放弃任务
	private JButton doGoodPraiseButton = new JButton("已经好评"); // 好评
	private JButton doGoodDegreeButton = new JButton("满意度评价"); // 满意度评价
	private JButton bindingBuyAccountButton = new JButton("绑定买号"); // 绑定买号
	private JButton paichuButton = new JButton("排除任务"); // 排除此任务,不再接
	private JComboBox<BuyAccount> buyAccountComboBox = new JComboBox<BuyAccount>();

	public BlueSkyeTaskDetailDialog() {
		super((Frame) null, "任务详细信息", true);
		logger.debug("BlueSkyeTaskDetailDialog newInstance..........");
		numberFormat.setMaximumFractionDigits(2);
		initStyle();
		initComponents();
	}

	public void setTask(Task task) {
		this.task = task;
		clearValue(); // 清空值
		refreshTaskButton(true); // 重置按钮状态

		doGetTaskDetail(); // 根据task获取task的详细信息

		List<BuyAccount> buyList = new ArrayList<BuyAccount>();
		buyList.add(BuyAccount.EMPTY);
		buyList.addAll(blueSky.getService().queryByEnable(true));
		buyAccountComboBox.setModel(new DefaultComboBoxModel<BuyAccount>(buyList.toArray(new BuyAccount[] {})));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if (COMMAND_SELECT_BUYACCOUNT.equals(actionCommand)) { // 选择绑定的买号
			buyAccountCreditLabel.setText(((BuyAccount) buyAccountComboBox.getSelectedItem()).getBuyDatas());
			return;
		}

		acceptTaskButton.setVisible(false);
		discardTaskButton.setVisible(false);
		doGoodPraiseButton.setVisible(false);
		doGoodDegreeButton.setVisible(false);
		bindingBuyAccountButton.setVisible(false);
		buyAccountComboBox.setEnabled(false);
		paichuButton.setVisible(false);

		switch (actionCommand) {
		case COMMAND_ACCEPT_TASK:
			doAcceptTask();
			break;
		case COMMAND_DISCARD_TASK:
			doDiscardTask();
			break;
		case COMMAND_DO_GOOD_PRAISE:
			doGoodPraise();
			break;
		case COMMAND_DO_GOOD_DEGREE:
			doGoodDegree();
			break;
		case COMMAND_BINDING_BUYACCOUNT:
			doBindingBuyAccount();
			break;
		case COMMAND_PAICHU_TASK:
			doPaichuTask();
			break;
		}
	}

	private void initStyle() {
		setSize(new Dimension(450, 600));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
		try {
			setIconImage(CoreUtil.getDefaultIco());
		} catch (Exception e) {
		}
	}

	private void initComponents() {
		paymentTypeLabel.setForeground(Color.RED);
		taskStatusLabel.setForeground(Color.RED);
		taskerAccountLabel.setForeground(Color.BLUE);
		acceptTaskButton.addActionListener(this);
		acceptTaskButton.setActionCommand(COMMAND_ACCEPT_TASK);
		paichuButton.addActionListener(this);
		paichuButton.setActionCommand(COMMAND_PAICHU_TASK);
		discardTaskButton.addActionListener(this);
		discardTaskButton.setActionCommand(COMMAND_DISCARD_TASK);
		doGoodPraiseButton.addActionListener(this);
		doGoodPraiseButton.setActionCommand(COMMAND_DO_GOOD_PRAISE);
		doGoodDegreeButton.addActionListener(this);
		doGoodDegreeButton.setActionCommand(COMMAND_DO_GOOD_DEGREE);
		bindingBuyAccountButton.addActionListener(this);
		bindingBuyAccountButton.setActionCommand(COMMAND_BINDING_BUYACCOUNT);
		buyAccountComboBox.addActionListener(this);
		buyAccountComboBox.setActionCommand(COMMAND_SELECT_BUYACCOUNT);

		HBaseBox box = HBaseBox.createVerticalBaseBox();
		box.setBorder(BorderFactory.createTitledBorder("任务详细信息"));
		// 好评要求时限： 1天后好评
		// 商铺动态评分： 全部5分
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("任务编号 : "), numberLabel, taskTypeLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("掌柜名称 : "), shopkeeperLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("宝贝标题 : "), itemTitleLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("支付方式 : "), paymentTypeLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("担保金额 : "), securityPriceLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("发布点数 : "), publishingPointLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("条    件 : "), isSincerityLabel, isIDCardLabel, isSearchLabel, isCollectLabel, isWangWangLabel, isReviewLabel, isQQLabel, isUpdatePriceLabel, isAddrLabel, isBaoGuoLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("任务状态 : "), taskStatusLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("接 收 人 : "), taskerAccountLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("好评内容 : "), praiseTextField));

		box.add(HBaseBox.createVerticalGlue());
		add(box, BorderLayout.CENTER);

		HBaseBox bottonBox = HBaseBox.createVerticalBaseBox();
		bottonBox.setBorder(BorderFactory.createTitledBorder(""));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("绑定买号 : "), buyAccountComboBox, HBaseBox.EmptyHorizontal, buyAccountCreditLabel, HBaseBox.EmptyHorizontal, bindingBuyAccountButton));
		bottonBox.add(HBaseBox.createHorizontalRight(paichuButton, HBaseBox.EmptyHorizontal, discardTaskButton, HBaseBox.EmptyHorizontal, acceptTaskButton, HBaseBox.EmptyHorizontal, doGoodPraiseButton, doGoodDegreeButton));

		add(bottonBox, BorderLayout.SOUTH);
	}

	/** 获取任务的详细信息限时到窗口上 */
	private void doGetTaskDetail() {
		if (task != null) {
			TaskUtil.executeSequenceTask(new Runnable() {
				@Override
				public void run() {
					_doGetTaskDetail();
				}
			}, GROPU);
		}
	}

	/** 接手任务 */
	private void doAcceptTask() {
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				TaskService service = blueSky.getService();
				service.webAcceptTask(task, new SuccessAndFailureCallBack() {
					@Override
					public void success(String success, Object object) {
						HAlert.showTips(success, "成功", BlueSkyeTaskDetailDialog.this);
					}

					@Override
					public void failure(String error, Object object) {
						HAlert.showWarnTips(error, BlueSkyeTaskDetailDialog.this);
					}
				});
				_doGetTaskDetail();
			}
		}, GROPU);
	}

	/** 放弃任务 */
	private void doDiscardTask() {
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				TaskService service = blueSky.getService();
				service.webDiscardTask(task, new SuccessAndFailureCallBack() {

					@Override
					public void success(String success, Object object) {
						HAlert.showTips(success, "成功", BlueSkyeTaskDetailDialog.this);
					}

					@Override
					public void failure(String error, Object object) {
						HAlert.showWarnTips(error, BlueSkyeTaskDetailDialog.this);
					}
				});
				_doGetTaskDetail();
			}
		}, GROPU);
	}

	/** 排除/启用任务 */
	private void doPaichuTask() {
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				TaskService service = blueSky.getService();
				service.doPaichuTask(task, new SuccessAndFailureCallBack() {
					@Override
					public void success(String success, Object object) {
						HAlert.showTips(success, "成功", BlueSkyeTaskDetailDialog.this);
					}

					@Override
					public void failure(String error, Object object) {
						HAlert.showWarnTips(error, BlueSkyeTaskDetailDialog.this);
					}
				});
			}
		}, GROPU);
	}

	// 已经好评
	private void doGoodPraise() {
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				TaskService service = blueSky.getService();
				service.webDoGoodPraise(task, new SuccessAndFailureCallBack() {
					@Override
					public void success(String success, Object object) {
						HAlert.showTips(success, "成功", BlueSkyeTaskDetailDialog.this);
					}

					@Override
					public void failure(String error, Object object) {
						HAlert.showWarnTips(error, BlueSkyeTaskDetailDialog.this);
					}
				});
				_doGetTaskDetail();
			}
		}, GROPU);

	}

	// 满意度评价
	private void doGoodDegree() {
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				TaskService service = blueSky.getService();
				service.webDoGoodDegree(task, new SuccessAndFailureCallBack() {
					@Override
					public void success(String success, Object object) {
						HAlert.showTips(success, "成功", BlueSkyeTaskDetailDialog.this);
					}

					@Override
					public void failure(String error, Object object) {
						HAlert.showWarnTips(error, BlueSkyeTaskDetailDialog.this);
					}
				});
				_doGetTaskDetail();
			}
		}, GROPU);
	}

	/** 绑定买号 */
	private void doBindingBuyAccount() {
		TaskUtil.executeSequenceTask(new Runnable() {
			@Override
			public void run() {
				TaskService service = blueSky.getService();
				BuyAccount buyAccount = (BuyAccount) buyAccountComboBox.getSelectedItem();
				service.webDoBindingBuyAccount(task, buyAccount, new SuccessAndFailureCallBack() {
					@Override
					public void success(String success, Object object) {
						HAlert.showTips(success, "成功", BlueSkyeTaskDetailDialog.this);
					}

					@Override
					public void failure(String error, Object object) {
						HAlert.showWarnTips(error, BlueSkyeTaskDetailDialog.this);
					}
				});
				_doGetTaskDetail();
			}
		}, GROPU);
	}

	private void _doGetTaskDetail() {
		TaskService service = blueSky.getService();
		if (!TaskStatus.排除.equals(task.getStatus())) {
			service.updateTaskDetail(task);
		}
		refreshTaskButton(false);

		// 设置各种详细信息
		numberLabel.setText(task.getNumber());
		taskTypeLabel.setIcon(task.getTaskTypeIcon());
		shopkeeperLabel.setText(task.getShopkeeper());
		itemTitleLabel.setText(task.getItemTitle());
		paymentTypeLabel.setText(task.getPaymentType().name());
		securityPriceLabel.setText(numberFormat.format(task.getSecurityPrice().doubleValue()));
		publishingPointLabel.setText(numberFormat.format(task.getPublishingPoint().doubleValue()));
		isUpdatePriceLabel.setIcon(task.getIsUpdatePriceIcon());
		isCollectLabel.setIcon(task.getIsCollectIcon());
		isSincerityLabel.setIcon(task.getIsSincerityIcon());
		isIDCardLabel.setIcon(task.getIsIDCardIcon());
		isSearchLabel.setIcon(task.getIsSearchIcon());
		isBaoGuoLabel.setIcon(task.getIsBaoGuoIcon());
		isQQLabel.setIcon(task.getIsUseQQIcon());
		isWangWangLabel.setIcon(task.getIsWangWangIcon());
		isAddrLabel.setIcon(task.getIsUpdateAddrIcon());
		isReviewLabel.setIcon(task.getIsReviewIcon());
		taskStatusLabel.setText(task.getStatus().name());
		taskerAccountLabel.setText(task.getTaskerAccount());
		praiseTextField.setText(task.getPraise());
		buyAccountCreditLabel.setText(((BuyAccount) buyAccountComboBox.getSelectedItem()).getBuyDatas());

		// 买号
		BuyAccount buy = blueSky.getService().findByBuyAccount(task.getTaskerBuyAccount());
		if (buy == null) {
			buyAccountComboBox.setSelectedItem(BuyAccount.EMPTY);
		} else {
			buyAccountComboBox.setSelectedItem(buy);
		}
	}

	private void clearValue() {
		// 设置各种详细信息
		numberLabel.setText(null);
		taskTypeLabel.setIcon(null);
		shopkeeperLabel.setText(null);
		itemTitleLabel.setText(null);
		paymentTypeLabel.setText(null);
		securityPriceLabel.setText(null);
		publishingPointLabel.setText(null);
		isUpdatePriceLabel.setIcon(null);
		isCollectLabel.setIcon(null);
		isSincerityLabel.setIcon(null);
		isIDCardLabel.setIcon(null);
		isSearchLabel.setIcon(null);
		isWangWangLabel.setIcon(null);
		isQQLabel.setIcon(null);
		isAddrLabel.setIcon(null);
		isBaoGuoLabel.setIcon(null);
		isReviewLabel.setIcon(null);
		taskStatusLabel.setText(null);
		taskerAccountLabel.setText(null);
		praiseTextField.setText(null);
		buyAccountCreditLabel.setText(null);
	}

	private void refreshTaskButton(boolean isHidden) {
		acceptTaskButton.setVisible(false);
		discardTaskButton.setVisible(false);
		doGoodPraiseButton.setVisible(false);
		doGoodDegreeButton.setVisible(false);
		bindingBuyAccountButton.setVisible(false);
		buyAccountComboBox.setEnabled(false);
		paichuButton.setVisible(false);
		paichuButton.setText("排除任务");

		if (!isHidden) {
			if (StringUtils.equals(blueSky.getLoginAccount(), task.getTaskerAccount())) {
				TaskStatus status = task.getStatus();
				if (status != null) {
					switch (status) {
					case 等待绑定买号:
						discardTaskButton.setVisible(true);
						bindingBuyAccountButton.setVisible(true);
						buyAccountComboBox.setEnabled(true);
						break;
					case 等待收货:
						// doGoodPraiseButton.setVisible(true);
						break;
					case 已完成_等待双方平台评价:
					case 已完成_等待发布方平台评价:
						// doGoodDegreeButton.setVisible(true);
						break;
					case 等待支付:
						discardTaskButton.setVisible(true);
						break;
					default:
						break;
					}
				}
			}

			// 根据任务信息设置控件状态
			if (TaskStatus.等待接手.equals(task.getStatus())) {
				acceptTaskButton.setVisible(true);
				paichuButton.setVisible(true);
			}

			// 排除状态,改为启用
			if (TaskStatus.排除.equals(task.getStatus())) {
				paichuButton.setText("启用任务");
				paichuButton.setVisible(true);
			}
		}

		// TODO 实现后删除
		doGoodPraiseButton.setVisible(false); // 暂时不实现,隐藏
		doGoodDegreeButton.setVisible(false); // 暂时不实现,隐藏
	}
}
