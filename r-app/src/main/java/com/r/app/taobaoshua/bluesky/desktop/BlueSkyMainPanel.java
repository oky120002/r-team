/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.desktop.tablemodel.TaskListTableModel;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.service.command.TaskQueryCommand;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;
import com.r.core.desktop.support.StringCellRenderer;
import com.r.core.desktop.support.ImageCellRenderer;
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
	private static final String COMMAN_LIST_SINCERITY_ALL = "comman_list_sincerity_all"; // 命令
	private static final String COMMAN_LIST_SINCERITY_INCLUDE = "comman_list_sincerity_include"; // 命令
	private static final String COMMAN_LIST_SINCERITY_EXCLUDE = "comman_list_sincerity_exclude"; // 命令
	private static final String COMMAN_LIST_IDCARD_ALL = "comman_list_idcard_all"; // 命令
	private static final String COMMAN_LIST_IDCARD_INCLUDE = "comman_list_idcard_include"; // 命令
	private static final String COMMAN_LIST_IDCARD_EXCLUDE = "comman_list_idcard_exclude"; // 命令
	private static final String COMMAN_LIST_SEARCH_ALL = "comman_list_search_all"; // 命令
	private static final String COMMAN_LIST_SEARCH_INCLUDE = "comman_list_search_include"; // 命令
	private static final String COMMAN_LIST_SEARCH_EXCLUDE = "comman_list_search_exclude"; // 命令
	private static final String COMMAN_LIST_COLLECT_ALL = "comman_list_collect_all"; // 命令
	private static final String COMMAN_LIST_COLLECT_INCLUDE = "comman_list_collect_include"; // 命令
	private static final String COMMAN_LIST_COLLECT_EXCLUDE = "comman_list_collect_exclude"; // 命令
	private static final String COMMAN_LIST_WANGWANG_ALL = "comman_list_wangwang_all"; // 命令
	private static final String COMMAN_LIST_WANGWANG_INCLUDE = "comman_list_wangwang_include"; // 命令
	private static final String COMMAN_LIST_WANGWANG_EXCLUDE = "comman_list_wangwang_exclude"; // 命令
	private static final String COMMAN_LIST_REVIEW_ALL = "comman_list_review_all"; // 命令
	private static final String COMMAN_LIST_REVIEW_INCLUDE = "comman_list_review_include"; // 命令
	private static final String COMMAN_LIST_REVIEW_EXCLUDE = "comman_list_review_exclude"; // 命令
	private static final String COMMAN_LIST_QQ_ALL = "comman_list_qq_all"; // 命令
	private static final String COMMAN_LIST_QQ_INCLUDE = "comman_list_qq_include"; // 命令
	private static final String COMMAN_LIST_QQ_EXCLUDE = "comman_list_qq_exclude"; // 命令

	private static final BlueSky blueSky = BlueSky.getInstance();

	// 查询条件分组
	private ButtonGroup sincerityGroup = new ButtonGroup();
	private ButtonGroup idcardGroup = new ButtonGroup();
	private ButtonGroup searchGroup = new ButtonGroup();
	private ButtonGroup collectGroup = new ButtonGroup();
	private ButtonGroup wangwangGroup = new ButtonGroup();
	private ButtonGroup reviewGroup = new ButtonGroup();
	private ButtonGroup qqGroup = new ButtonGroup();

	private JTable taskListTable; // 任务列表
	private TaskListTableModel taskListTableModel; // 任务列表数据源
	private JButton taskInfosButton = new JButton("获取任务信息");

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
		case COMMAN_LIST_SINCERITY_ALL:
		case COMMAN_LIST_SINCERITY_INCLUDE:
		case COMMAN_LIST_SINCERITY_EXCLUDE:
		case COMMAN_LIST_IDCARD_ALL:
		case COMMAN_LIST_IDCARD_INCLUDE:
		case COMMAN_LIST_IDCARD_EXCLUDE:
		case COMMAN_LIST_SEARCH_ALL:
		case COMMAN_LIST_SEARCH_INCLUDE:
		case COMMAN_LIST_SEARCH_EXCLUDE:
		case COMMAN_LIST_COLLECT_ALL:
		case COMMAN_LIST_COLLECT_INCLUDE:
		case COMMAN_LIST_COLLECT_EXCLUDE:
		case COMMAN_LIST_WANGWANG_ALL:
		case COMMAN_LIST_WANGWANG_INCLUDE:
		case COMMAN_LIST_WANGWANG_EXCLUDE:
		case COMMAN_LIST_REVIEW_ALL:
		case COMMAN_LIST_REVIEW_INCLUDE:
		case COMMAN_LIST_REVIEW_EXCLUDE:
		case COMMAN_LIST_QQ_ALL:
		case COMMAN_LIST_QQ_INCLUDE:
		case COMMAN_LIST_QQ_EXCLUDE:

			doTaskInfos();
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
		taskListTable.setDefaultRenderer(String.class, new StringCellRenderer()); // 支持渲染图片
		taskListTable.setDefaultRenderer(Image.class, new ImageCellRenderer()); // 支持渲染图片
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
		buttomBox.add(taskInfosButton);
		add(buttomBox, BorderLayout.SOUTH);
	}

	private void initListeners() {
		this.taskInfosButton.addActionListener(this);
		this.taskInfosButton.setActionCommand(COMMAND_TASKINFO);
	}

	/** 返回限制条件项第一行 */
	private Component getTaskListByOne() {
		HBaseBox box = HBaseBox.createHorizontalBaseBox();
		initSincerityBox(box); // 商保
		initIDCardBox(box); // 认证
		initSearchBox(box); // 搜索
		initCollectBox(box); // 收藏
		initWangWangBox(box); // 旺聊
		initReviewBox(box); // 审核
		box.add(HBaseBox.createHorizontalGlue());
		return box;
	}

	/** 返回限制条件项第一行 */
	private Component getTaskListByTwo() {
		HBaseBox box = HBaseBox.createHorizontalBaseBox();
		initQQBox(box);// QQ
		box.add(HBaseBox.createHorizontalGlue());
		return box;
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
		radio1.setActionCommand(COMMAN_LIST_SINCERITY_ALL);
		radio2.setActionCommand(COMMAN_LIST_SINCERITY_INCLUDE);
		radio3.setActionCommand(COMMAN_LIST_SINCERITY_EXCLUDE);
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
		radio1.setActionCommand(COMMAN_LIST_IDCARD_ALL);
		radio2.setActionCommand(COMMAN_LIST_IDCARD_INCLUDE);
		radio3.setActionCommand(COMMAN_LIST_IDCARD_EXCLUDE);
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
		radio1.setActionCommand(COMMAN_LIST_SEARCH_ALL);
		radio2.setActionCommand(COMMAN_LIST_SEARCH_INCLUDE);
		radio3.setActionCommand(COMMAN_LIST_SEARCH_EXCLUDE);
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
		radio1.setActionCommand(COMMAN_LIST_COLLECT_ALL);
		radio2.setActionCommand(COMMAN_LIST_COLLECT_INCLUDE);
		radio3.setActionCommand(COMMAN_LIST_COLLECT_EXCLUDE);
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
		radio1.setActionCommand(COMMAN_LIST_WANGWANG_ALL);
		radio2.setActionCommand(COMMAN_LIST_WANGWANG_INCLUDE);
		radio3.setActionCommand(COMMAN_LIST_WANGWANG_EXCLUDE);
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
		radio1.setActionCommand(COMMAN_LIST_REVIEW_ALL);
		radio2.setActionCommand(COMMAN_LIST_REVIEW_INCLUDE);
		radio3.setActionCommand(COMMAN_LIST_REVIEW_EXCLUDE);
		radio1.setSelected(true);
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
		radio1.setActionCommand(COMMAN_LIST_QQ_ALL);
		radio2.setActionCommand(COMMAN_LIST_QQ_INCLUDE);
		radio3.setActionCommand(COMMAN_LIST_QQ_EXCLUDE);
		radio1.setSelected(true);
		HBaseBox b = HBaseBox.createHorizontalBaseBox();
		b.setBorder(BorderFactory.createTitledBorder("QQ"));
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
				try {
					taskInfosButton.setEnabled(false);
					taskInfosButton.setText("获取中...");

					TaskQueryCommand query = new TaskQueryCommand();
					query.setStatus(TaskStatus.等待接手);
					query.setPage(0, 100);
					query.setOrder(2);
					select(query);

					List<Task> tasks = blueSky.getService().execQueryCommand(query);
					taskListTableModel.setTasks(tasks);
					taskListTable.updateUI();

					taskInfosButton.setText("获取任务信息");
					taskInfosButton.setEnabled(true);
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug(e.getMessage());
					taskInfosButton.setEnabled(true);
				}
			}

			private void select(TaskQueryCommand query) {
				// 商保
				ButtonModel selection = sincerityGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAN_LIST_SINCERITY_INCLUDE:
						query.setIsSincerity(true);
						break;
					case COMMAN_LIST_SINCERITY_EXCLUDE:
						query.setIsSincerity(false);
						break;
					}
				}

				// 认证
				selection = idcardGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAN_LIST_IDCARD_INCLUDE:
						query.setIsIDCard(true);
						break;
					case COMMAN_LIST_IDCARD_EXCLUDE:
						query.setIsIDCard(false);
						break;
					}
				}

				// 搜索
				selection = searchGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAN_LIST_SEARCH_INCLUDE:
						query.setIsSearch(true);
						break;
					case COMMAN_LIST_SEARCH_EXCLUDE:
						query.setIsSearch(false);
						break;
					}
				}

				// 收藏
				selection = collectGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAN_LIST_COLLECT_INCLUDE:
						query.setIsCollect(true);
						break;
					case COMMAN_LIST_COLLECT_EXCLUDE:
						query.setIsCollect(false);
						break;
					}
				}

				// 旺聊
				selection = wangwangGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAN_LIST_WANGWANG_INCLUDE:
						query.setIsWangWang(true);
						break;
					case COMMAN_LIST_WANGWANG_EXCLUDE:
						query.setIsWangWang(false);
						break;
					}
				}

				// 审核
				selection = reviewGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAN_LIST_REVIEW_INCLUDE:
						query.setIsReview(true);
						break;
					case COMMAN_LIST_REVIEW_EXCLUDE:
						query.setIsReview(false);
						break;
					}
				}

				// QQ
				selection = qqGroup.getSelection();
				if (selection != null) {
					String actionCommand = selection.getActionCommand();
					switch (actionCommand) {
					case COMMAN_LIST_QQ_INCLUDE:
						query.setIsUseQQ(true);
						break;
					case COMMAN_LIST_QQ_EXCLUDE:
						query.setIsUseQQ(false);
						break;
					}
				}
			}
		});
	}
}
