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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.bluesky.BlueSky;
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
	private static final String COMMAND_DISCARD_TASK = "command_discard_task"; // 命令_放弃任务
	private static final String COMMAND_DO_GOOD_PRAISE = "command_do_good_praise"; // 命令_已经好评
	private Task task = new Task(); // 任务

	private JLabel numberLabel = new JLabel(); // 任务编号
	private JLabel taskTypeLabel = new JLabel(); // 任务类型
	private JLabel shopkeeperLabel = new JLabel(); // 掌柜名称
	private JLabel itemTitleLabel = new JLabel(); // 宝贝标题
	private JLabel paymentTypeLabel = new JLabel(); // 支付方式
	private JLabel securityPriceLabel = new JLabel(); // 担保金额
	private JLabel publishingPointLabel = new JLabel(); // 发布点数
	private JLabel isUpdatePriceLabel = new JLabel(); // 是否改价
	private JLabel isSincerityLabel = new JLabel(); // 商保
	private JLabel isCollectLabel = new JLabel(); // 收藏
	private JLabel taskStatusLabel = new JLabel(); // 状态
	private JLabel taskerAccountLabel = new JLabel(); // 接收人账号
	private JTextField praiseTextField = new JTextField(); // 好评内容

	private JButton acceptTaskButton = new JButton("接手任务"); // 接手任务
	private JButton discardTaskButton = new JButton("放弃任务");// 放弃任务
	private JButton doGoodPraiseButton = new JButton("已经好评"); // 好评

	public BlueSkyeTaskDetailDialog() {
		super((Frame) null, "任务详细信息", true);
		logger.debug("BlueSkyeTaskDetailDialog newInstance..........");
		numberFormat.setMaximumFractionDigits(2);
		initStyle();
		initComponents();
	}

	public void setTask(Task task) {
		this.task = task;
		doGetTaskDetail();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		acceptTaskButton.setEnabled(false);
		discardTaskButton.setEnabled(false);
		doGoodPraiseButton.setEnabled(false);

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
		discardTaskButton.addActionListener(this);
		discardTaskButton.setActionCommand(COMMAND_DISCARD_TASK);

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
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("条    件 : "), isSincerityLabel, isCollectLabel, isUpdatePriceLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("任务状态 : "), taskStatusLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("接 收 人 : "), taskerAccountLabel));
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("好评内容 : "), praiseTextField));

		box.add(HBaseBox.createVerticalGlue());
		add(box, BorderLayout.CENTER);

		HBaseBox bottonBox = HBaseBox.createVerticalBaseBox();
		bottonBox.setBorder(BorderFactory.createTitledBorder(""));
		bottonBox.add(HBaseBox.createHorizontalRight(discardTaskButton, HBaseBox.EmptyHorizontal, acceptTaskButton, HBaseBox.EmptyHorizontal, doGoodPraiseButton));

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
				service.acceptTask(task, new SuccessAndFailureCallBack() {
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
				service.discardTask(task, new SuccessAndFailureCallBack() {

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

	// 已经好评
	private void doGoodPraise() {

	}

	private void _doGetTaskDetail() {
		TaskService service = blueSky.getService();
		service.updateTaskDetail(task);
		refreshTaskButton();

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
		taskStatusLabel.setText(task.getStatus().name());
		taskerAccountLabel.setText(task.getTaskerAccount());
		praiseTextField.setText(task.getPraise());
	}

	private void refreshTaskButton() {
		discardTaskButton.setEnabled(false);
		doGoodPraiseButton.setEnabled(false);
		acceptTaskButton.setEnabled(false);

		if (StringUtils.equals(blueSky.getLoginAccount(), task.getTaskerAccount())) {
			// 根据任务信息设置控件状态
			if (TaskStatus.等待绑定买号.equals(task.getStatus())) {
				discardTaskButton.setEnabled(true);
			}
			if (TaskStatus.等待收货.equals(task.getStatus())) {
				doGoodPraiseButton.setEnabled(true);
			}
		}

		// 根据任务信息设置控件状态
		if (TaskStatus.等待接手.equals(task.getStatus())) {
			acceptTaskButton.setEnabled(true);
		}
	}
}
