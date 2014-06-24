/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.desktop.tablemodel.TaskListTableModel;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.app.taobaoshua.bluesky.service.command.TaskQueryCommand;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.support.TitleCellRenderer;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * @author oky
 * 
 */
public class BlueSkyMainPanel extends HBasePanel implements ActionListener {
	private static final long serialVersionUID = -2648127087894579928L;
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyMainPanel.class);
	private static final String COMMAND_TASKINFO = "command_taskinfo"; // 命令_获取任务信息
	private static final String COMMAND_CHECK_TASKSTATUS = "command_check_taskstatus";// 命令_校验任务状态
	private static final String COMMAND_LIST_TASKSTATUS = "command_list_taskstatus"; // 命令
	private static final String COMMAND_LIST_SINCERITY_ALL = "command_list_sincerity_all"; // 命令
	private static final String COMMAND_LIST_SINCERITY_INCLUDE = "command_list_sincerity_include"; // 命令
	private static final String COMMAND_LIST_SINCERITY_EXCLUDE = "command_list_sincerity_exclude"; // 命令
	private static final String COMMAND_LIST_IDCARD_ALL = "command_list_idcard_all"; // 命令
	private static final String COMMAND_LIST_IDCARD_INCLUDE = "command_list_idcard_include"; // 命令
	private static final String COMMAND_LIST_IDCARD_EXCLUDE = "command_list_idcard_exclude"; // 命令
	private static final String COMMAND_LIST_SEARCH_ALL = "command_list_search_all"; // 命令
	private static final String COMMAND_LIST_SEARCH_INCLUDE = "command_list_search_include"; // 命令
	private static final String COMMAND_LIST_SEARCH_EXCLUDE = "command_list_search_exclude"; // 命令
	private static final String COMMAND_LIST_COLLECT_ALL = "command_list_collect_all"; // 命令
	private static final String COMMAND_LIST_COLLECT_INCLUDE = "command_list_collect_include"; // 命令
	private static final String COMMAND_LIST_COLLECT_EXCLUDE = "command_list_collect_exclude"; // 命令
	private static final String COMMAND_LIST_WANGWANG_ALL = "command_list_wangwang_all"; // 命令
	private static final String COMMAND_LIST_WANGWANG_INCLUDE = "command_list_wangwang_include"; // 命令
	private static final String COMMAND_LIST_WANGWANG_EXCLUDE = "command_list_wangwang_exclude"; // 命令
	private static final String COMMAND_LIST_REVIEW_ALL = "command_list_review_all"; // 命令
	private static final String COMMAND_LIST_REVIEW_INCLUDE = "command_list_review_include"; // 命令
	private static final String COMMAND_LIST_REVIEW_EXCLUDE = "command_list_review_exclude"; // 命令
	private static final String COMMAND_LIST_QQ_ALL = "command_list_qq_all"; // 命令
	private static final String COMMAND_LIST_QQ_INCLUDE = "command_list_qq_include"; // 命令
	private static final String COMMAND_LIST_QQ_EXCLUDE = "command_list_qq_exclude"; // 命令
	private static final String COMMAND_LIST_UPDATEPRICE_ALL = "command_list_updateprice_all"; // 命令
	private static final String COMMAND_LIST_UPDATEPRICE_INCLUDE = "command_list_updateprice_include"; // 命令
	private static final String COMMAND_LIST_UPDATEPRICE_EXCLUDE = "command_list_updateprice_exclude"; // 命令

	private static final BlueSky blueSky = BlueSky.getInstance();

	// 查询条件分组
	private ButtonGroup sincerityGroup = new ButtonGroup();
	private ButtonGroup idcardGroup = new ButtonGroup();
	private ButtonGroup searchGroup = new ButtonGroup();
	private ButtonGroup collectGroup = new ButtonGroup();
	private ButtonGroup wangwangGroup = new ButtonGroup();
	private ButtonGroup reviewGroup = new ButtonGroup();
	private ButtonGroup qqGroup = new ButtonGroup();
	private ButtonGroup updatepriceGroup = new ButtonGroup();
	private JComboBox<TaskStatus> taskStatusComboBox = new JComboBox<TaskStatus>(TaskStatus.values());

	// 列表
	private JTable taskListTable; // 任务列表
	private TaskListTableModel taskListTableModel; // 任务列表数据源

	// 功能区
	private JButton taskInfosButton = new JButton("获取任务信息");
	private JButton checkTaskStatusButton = new JButton("校验任务状态");

	// 任务详细信息窗口
	private BlueSkyeTaskDetailDialog taskDetaiDialog = new BlueSkyeTaskDetailDialog();

	public BlueSkyMainPanel() {
		super();
		logger.debug("BlueSkyMainPanel newInstance..........");
		initStyle();
		initComponents();
		initListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		switch (actionCommand) {
		case COMMAND_TASKINFO:
		case COMMAND_LIST_TASKSTATUS:
		case COMMAND_LIST_SINCERITY_ALL:
		case COMMAND_LIST_SINCERITY_INCLUDE:
		case COMMAND_LIST_SINCERITY_EXCLUDE:
		case COMMAND_LIST_IDCARD_ALL:
		case COMMAND_LIST_IDCARD_INCLUDE:
		case COMMAND_LIST_IDCARD_EXCLUDE:
		case COMMAND_LIST_SEARCH_ALL:
		case COMMAND_LIST_SEARCH_INCLUDE:
		case COMMAND_LIST_SEARCH_EXCLUDE:
		case COMMAND_LIST_COLLECT_ALL:
		case COMMAND_LIST_COLLECT_INCLUDE:
		case COMMAND_LIST_COLLECT_EXCLUDE:
		case COMMAND_LIST_WANGWANG_ALL:
		case COMMAND_LIST_WANGWANG_INCLUDE:
		case COMMAND_LIST_WANGWANG_EXCLUDE:
		case COMMAND_LIST_REVIEW_ALL:
		case COMMAND_LIST_REVIEW_INCLUDE:
		case COMMAND_LIST_REVIEW_EXCLUDE:
		case COMMAND_LIST_QQ_ALL:
		case COMMAND_LIST_QQ_INCLUDE:
		case COMMAND_LIST_QQ_EXCLUDE:
		case COMMAND_LIST_UPDATEPRICE_ALL:
		case COMMAND_LIST_UPDATEPRICE_INCLUDE:
		case COMMAND_LIST_UPDATEPRICE_EXCLUDE:
			doTaskInfos();
			break;
		case COMMAND_CHECK_TASKSTATUS: // 校验任务状态
			doCheckTaskStatus();
			break;
		}
	}

	private void initStyle() {
		setLayout(new BorderLayout());
	}

	private void initComponents() {

		// 列表条件过滤区域
		HBaseBox topBox = HBaseBox.createVerticalBaseBox();
		topBox.setBorder(BorderFactory.createTitledBorder("条件过滤"));
		topBox.add(getTaskListByOne());
		topBox.add(getTaskListByTwo());
		add(topBox, BorderLayout.NORTH);

		// PV和PV任务列表
		HBaseBox centerBox = HBaseBox.createVerticalBaseBox();
		taskListTableModel = new TaskListTableModel();
		taskListTable = new JTable(taskListTableModel);
		taskListTable.setDefaultRenderer(String.class, new TitleCellRenderer()); // 支持Title提示
		taskListTable.addMouseListener(new TaskListMouseAdapter());
		taskListTable.setFillsViewportHeight(true);
		taskListTable.setDragEnabled(false);
		taskListTableModel.setColWidth(taskListTable);
		taskListTable.doLayout();
		centerBox.add(new JScrollPane(taskListTable));
		add(centerBox, BorderLayout.CENTER);

		// 按钮区域
		HBaseBox buttomBox = HBaseBox.createHorizontalBaseBox();
		buttomBox.setBorder(BorderFactory.createTitledBorder("功能区"));
		buttomBox.add(HBaseBox.createHorizontalGlue());
		buttomBox.add(checkTaskStatusButton);
		buttomBox.add(HBaseBox.createHorizontalStrut(5));
		buttomBox.add(taskInfosButton);
		add(buttomBox, BorderLayout.SOUTH);
	}

	private void initListeners() {
		this.taskInfosButton.addActionListener(this);
		this.taskInfosButton.setActionCommand(COMMAND_TASKINFO);
		this.checkTaskStatusButton.addActionListener(this);
		this.checkTaskStatusButton.setActionCommand(COMMAND_CHECK_TASKSTATUS);
	}

	/** 返回限制条件项第一行 */
	private Component getTaskListByOne() {
		HBaseBox box = HBaseBox.createHorizontalBaseBox();
		initTaskStatusBox(box); // 任务状态
		initSincerityBox(box); // 商保
		initIDCardBox(box); // 认证
		initSearchBox(box); // 搜索
		initCollectBox(box); // 收藏
		initWangWangBox(box); // 旺聊
		box.add(HBaseBox.createHorizontalGlue());
		return box;
	}

	/** 返回限制条件项第一行 */
	private Component getTaskListByTwo() {
		HBaseBox box = HBaseBox.createHorizontalBaseBox();
		initReviewBox(box); // 审核
		initUpdatePriceBox(box); // 是否需要改价
		initQQBox(box);// QQ
		box.add(HBaseBox.createHorizontalGlue());
		return box;
	}

	/** 任务状态 */
	private void initTaskStatusBox(HBaseBox box) {
		taskStatusComboBox.setSelectedItem(TaskStatus.等待接手);
		taskStatusComboBox.addActionListener(this);
		taskStatusComboBox.setActionCommand(COMMAND_LIST_TASKSTATUS);
		taskStatusComboBox.setFocusable(false);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("任务状态"));
		b.add(taskStatusComboBox);
		box.add(b);
	}

	// 商保
	private void initSincerityBox(HBaseBox box) {
		JRadioButton radio1 = new JRadioButton("默认");
		JRadioButton radio2 = new JRadioButton("包含");
		JRadioButton radio3 = new JRadioButton("排除");
		sincerityGroup.add(radio1);
		sincerityGroup.add(radio2);
		sincerityGroup.add(radio3);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio1.setActionCommand(COMMAND_LIST_SINCERITY_ALL);
		radio2.setActionCommand(COMMAND_LIST_SINCERITY_INCLUDE);
		radio3.setActionCommand(COMMAND_LIST_SINCERITY_EXCLUDE);
		radio1.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("商保"));
		b.add(radio1);
		b.add(radio2);
		b.add(radio3);
		box.add(b);
	}

	// 认证
	private void initIDCardBox(HBaseBox box) {
		JRadioButton radio1 = new JRadioButton("默认");
		JRadioButton radio2 = new JRadioButton("包含");
		JRadioButton radio3 = new JRadioButton("排除");
		idcardGroup.add(radio1);
		idcardGroup.add(radio2);
		idcardGroup.add(radio3);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio1.setActionCommand(COMMAND_LIST_IDCARD_ALL);
		radio2.setActionCommand(COMMAND_LIST_IDCARD_INCLUDE);
		radio3.setActionCommand(COMMAND_LIST_IDCARD_EXCLUDE);
		radio1.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("认证"));
		b.add(radio1);
		b.add(radio2);
		b.add(radio3);
		box.add(b);
	}

	// 搜索
	private void initSearchBox(HBaseBox box) {
		JRadioButton radio1 = new JRadioButton("默认");
		JRadioButton radio2 = new JRadioButton("包含");
		JRadioButton radio3 = new JRadioButton("排除");
		searchGroup.add(radio1);
		searchGroup.add(radio2);
		searchGroup.add(radio3);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio1.setActionCommand(COMMAND_LIST_SEARCH_ALL);
		radio2.setActionCommand(COMMAND_LIST_SEARCH_INCLUDE);
		radio3.setActionCommand(COMMAND_LIST_SEARCH_EXCLUDE);
		radio1.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("搜索"));
		b.add(radio1);
		b.add(radio2);
		b.add(radio3);
		box.add(b);
	}

	// 收藏
	private void initCollectBox(HBaseBox box) {
		JRadioButton radio1 = new JRadioButton("默认");
		JRadioButton radio2 = new JRadioButton("包含");
		JRadioButton radio3 = new JRadioButton("排除");
		collectGroup.add(radio1);
		collectGroup.add(radio2);
		collectGroup.add(radio3);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio1.setActionCommand(COMMAND_LIST_COLLECT_ALL);
		radio2.setActionCommand(COMMAND_LIST_COLLECT_INCLUDE);
		radio3.setActionCommand(COMMAND_LIST_COLLECT_EXCLUDE);
		radio1.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("收藏"));
		b.add(radio1);
		b.add(radio2);
		b.add(radio3);
		box.add(b);
	}

	// 旺聊
	private void initWangWangBox(HBaseBox box) {
		JRadioButton radio1 = new JRadioButton("默认");
		JRadioButton radio2 = new JRadioButton("包含");
		JRadioButton radio3 = new JRadioButton("排除");
		wangwangGroup.add(radio1);
		wangwangGroup.add(radio2);
		wangwangGroup.add(radio3);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio1.setActionCommand(COMMAND_LIST_WANGWANG_ALL);
		radio2.setActionCommand(COMMAND_LIST_WANGWANG_INCLUDE);
		radio3.setActionCommand(COMMAND_LIST_WANGWANG_EXCLUDE);
		radio1.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("旺聊"));
		b.add(radio1);
		b.add(radio2);
		b.add(radio3);
		box.add(b);
	}

	// 审核
	private void initReviewBox(HBaseBox box) {
		JRadioButton radio1 = new JRadioButton("默认");
		JRadioButton radio2 = new JRadioButton("包含");
		JRadioButton radio3 = new JRadioButton("排除");
		reviewGroup.add(radio1);
		reviewGroup.add(radio2);
		reviewGroup.add(radio3);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio1.setActionCommand(COMMAND_LIST_REVIEW_ALL);
		radio2.setActionCommand(COMMAND_LIST_REVIEW_INCLUDE);
		radio3.setActionCommand(COMMAND_LIST_REVIEW_EXCLUDE);
		radio3.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("审核"));
		b.add(radio1);
		b.add(radio2);
		b.add(radio3);
		box.add(b);
	}

	// QQ
	private void initQQBox(HBaseBox box) {
		JRadioButton radio1 = new JRadioButton("默认");
		JRadioButton radio2 = new JRadioButton("包含");
		JRadioButton radio3 = new JRadioButton("排除");
		qqGroup.add(radio1);
		qqGroup.add(radio2);
		qqGroup.add(radio3);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio1.setActionCommand(COMMAND_LIST_QQ_ALL);
		radio2.setActionCommand(COMMAND_LIST_QQ_INCLUDE);
		radio3.setActionCommand(COMMAND_LIST_QQ_EXCLUDE);
		radio3.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("QQ"));
		b.add(radio1);
		b.add(radio2);
		b.add(radio3);
		box.add(b);
	}

	// 改价
	private void initUpdatePriceBox(HBaseBox box) {
		JRadioButton radio1 = new JRadioButton("默认");
		JRadioButton radio2 = new JRadioButton("包含");
		JRadioButton radio3 = new JRadioButton("排除");
		updatepriceGroup.add(radio1);
		updatepriceGroup.add(radio2);
		updatepriceGroup.add(radio3);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio1.setActionCommand(COMMAND_LIST_UPDATEPRICE_ALL);
		radio2.setActionCommand(COMMAND_LIST_UPDATEPRICE_INCLUDE);
		radio3.setActionCommand(COMMAND_LIST_UPDATEPRICE_EXCLUDE);
		radio3.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("改价"));
		b.add(radio1);
		b.add(radio2);
		b.add(radio3);
		box.add(b);
	}

	/** 获取任务信息 */
	private void doTaskInfos() {
		TaskUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				taskInfosButton.setEnabled(false);
				try {
					TaskQueryCommand query = new TaskQueryCommand();
					query.setStatus(TaskStatus.等待接手);
					query.setPage(0, 100);
					query.setOrder(2);
					select(query);

					List<Task> tasks = blueSky.getService().execQueryCommand(query);
					taskListTableModel.setTasks(tasks);
					taskListTable.updateUI();

				} catch (Exception e) {
					e.printStackTrace();
					logger.debug(e.getMessage());
				}
				taskInfosButton.setEnabled(true);
			}

			private void select(TaskQueryCommand query) {
				// 任务状态
				query.setStatus((TaskStatus) taskStatusComboBox.getSelectedItem());

				// 商保
				ButtonModel selection = sincerityGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAND_LIST_SINCERITY_INCLUDE:
						query.setIsSincerity(true);
						break;
					case COMMAND_LIST_SINCERITY_EXCLUDE:
						query.setIsSincerity(false);
						break;
					}
				}

				// 认证
				selection = idcardGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAND_LIST_IDCARD_INCLUDE:
						query.setIsIDCard(true);
						break;
					case COMMAND_LIST_IDCARD_EXCLUDE:
						query.setIsIDCard(false);
						break;
					}
				}

				// 搜索
				selection = searchGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAND_LIST_SEARCH_INCLUDE:
						query.setIsSearch(true);
						break;
					case COMMAND_LIST_SEARCH_EXCLUDE:
						query.setIsSearch(false);
						break;
					}
				}

				// 收藏
				selection = collectGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAND_LIST_COLLECT_INCLUDE:
						query.setIsCollect(true);
						break;
					case COMMAND_LIST_COLLECT_EXCLUDE:
						query.setIsCollect(false);
						break;
					}
				}

				// 旺聊
				selection = wangwangGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAND_LIST_WANGWANG_INCLUDE:
						query.setIsWangWang(true);
						break;
					case COMMAND_LIST_WANGWANG_EXCLUDE:
						query.setIsWangWang(false);
						break;
					}
				}

				// 审核
				selection = reviewGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAND_LIST_REVIEW_INCLUDE:
						query.setIsReview(true);
						break;
					case COMMAND_LIST_REVIEW_EXCLUDE:
						query.setIsReview(false);
						break;
					}
				}

				// QQ
				selection = qqGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAND_LIST_QQ_INCLUDE:
						query.setIsUseQQ(true);
						break;
					case COMMAND_LIST_QQ_EXCLUDE:
						query.setIsUseQQ(false);
						break;
					}
				}

				// QQ
				selection = updatepriceGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAND_LIST_UPDATEPRICE_INCLUDE:
						query.setIsUpdatePrice(true);
						break;
					case COMMAND_LIST_UPDATEPRICE_EXCLUDE:
						query.setIsUpdatePrice(false);
						break;
					}
				}
			}
		});
	}

	/** 校验任务状态 */
	private void doCheckTaskStatus() {
		if (!blueSky.isLogin()) {
			HAlert.showWarnTips("此功能只能登陆后才能使用", this);
		}
		TaskUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				checkTaskStatusButton.setEnabled(false);
				try {
					int[] rowIndexs = taskListTable.getSelectedRows();
					taskListTable.clearSelection();
					if (ArrayUtils.isNotEmpty(rowIndexs)) {
						TaskService service = blueSky.getService();
						Collection<Task> tasks = taskListTableModel.getRowTask(rowIndexs);
						for (Task task : tasks) {
							if (task != null) {
								checkTaskStatusButton.setEnabled(false);
								service.updateTaskDetail(task);
								TaskUtil.sleep(1_000);
								logger.debug("更新任务{}的详细信息 --- 任务状态 : {}", task.getNumber(), task.getStatus().name());
							}
						}
						taskListTable.updateUI();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug(e.getMessage());
				}
				checkTaskStatusButton.setEnabled(true);
			}
		});
	}

	private class TaskListMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (blueSky.isLogin()) {
				if (2 <= e.getClickCount()) { // 双击
					Collection<Task> tasks = taskListTableModel.getRowTask(taskListTable.getSelectedRow());
					if (CollectionUtils.isNotEmpty(tasks)) {
						taskDetaiDialog.setTask(tasks.iterator().next());
						taskDetaiDialog.setVisible(true);
					}
				}
			}
		}
	}
}
