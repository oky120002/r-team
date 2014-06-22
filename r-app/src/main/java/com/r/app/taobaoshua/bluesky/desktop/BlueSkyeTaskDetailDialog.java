/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskType;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.app.taobaoshua.core.CoreUtil;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * @author Administrator
 *
 */
public class BlueSkyeTaskDetailDialog extends HBaseDialog {
	private static final long serialVersionUID = -7140304585521247842L;
	private static final String GROPU = BlueSkyeTaskDetailDialog.class.getName();
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyeTaskDetailDialog.class);
	private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
	private static final BlueSky blueSky = BlueSky.getInstance();
	private Task task = new Task(); // 任务

	private JLabel numberLabel = new JLabel(""); // 任务编号
	private JLabel taskTypeLabel = new JLabel(""); // 任务类型
	private JLabel shopkeeperLabel = new JLabel(""); // 掌柜名称
	private JLabel itemTitleLabel = new JLabel(""); // 宝贝标题
	private JLabel paymentTypeLabel = new JLabel(""); // 支付方式
	private JLabel securityPriceLabel = new JLabel(""); // 担保金额
	private JLabel publishingPointLabel = new JLabel(""); // 发布点数
	private JLabel isUpdatePriceLabel = new JLabel(""); // 是否改价

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
		box.adds(HBaseBox.createHorizontalLeft(new JLabel("是否改价 : "), isUpdatePriceLabel));

		paymentTypeLabel.setForeground(Color.RED);

		add(box, BorderLayout.CENTER);
	}

	/** 获取任务的详细信息限时到窗口上 */
	private void doGetTaskDetail() {
		if (task != null) {
			TaskUtil.executeSequenceTask(new Runnable() {
				@Override
				public void run() {
					TaskService service = blueSky.getService();
					service.updateTaskDetail(task);

					// 设置各种详细信息
					numberLabel.setText(task.getNumber());
					taskTypeLabel.setIcon(task.getTaskTypeIcon());
					shopkeeperLabel.setText(task.getShopkeeper());
					itemTitleLabel.setText(task.getItemTitle());
					paymentTypeLabel.setText(task.getPaymentType().name());
					securityPriceLabel.setText(numberFormat.format(task.getSecurityPrice().doubleValue()));
					publishingPointLabel.setText(numberFormat.format(task.getPublishingPoint().doubleValue()));
					if (task.isUpdatePrice()) {
						isUpdatePriceLabel.setIcon(blueSky.getIcon("gai.gif"));
						isUpdatePriceLabel.setText("");
					} else {
						isUpdatePriceLabel.setIcon(null);
						isUpdatePriceLabel.setText("否");
					}

				}
			}, GROPU);
		}
	}
}
