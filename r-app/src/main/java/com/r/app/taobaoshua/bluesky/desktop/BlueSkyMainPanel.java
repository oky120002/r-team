/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.desktop.tablemodel.TaskListTableModel;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;
import com.r.core.desktop.ctrl.HBaseScrollPane;
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
	private static final BlueSky blueSky = BlueSky.getInstance();

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
		JComponent source = (JComponent) e.getSource();

		source.setEnabled(false);
		switch (actionCommand) {
		case COMMAND_TASKINFO:
			doTaskInfos();
			break;
		}
	}

	private void initStyle() {

	}

	private void initComponents() {
		// PV和PV任务列表
		HBaseBox centerBox = HBaseBox.createVerticalBaseBox();
		taskListTableModel = new TaskListTableModel();
		taskListTable = new JTable(taskListTableModel);
		taskListTable.setDefaultRenderer(Object.class, new ImageCellRenderer());
		taskListTable.setFillsViewportHeight(true);
		taskListTable.setDragEnabled(false);
		taskListTable.doLayout();
		centerBox.add(new HBaseScrollPane(taskListTable));
		add(centerBox, BorderLayout.CENTER);

		// 按钮区域
		HBaseBox buttomBox = HBaseBox.createHorizontalBaseBox();
		buttomBox.add(HBaseBox.createHorizontalGlue());
		buttomBox.add(taskInfosButton);
		add(buttomBox, BorderLayout.SOUTH);

	}

	private void initListeners() {
		this.taskInfosButton.addActionListener(this);
		this.taskInfosButton.setActionCommand(COMMAND_TASKINFO);
	}

	/** 获取任务信息 */
	private void doTaskInfos() {
		TaskUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				taskInfosButton.setEnabled(false);
				taskInfosButton.setText("获取中...");
				taskListTable.updateUI();
				taskInfosButton.setText("获取任务信息");
				taskInfosButton.setEnabled(true);
			}
		});
	}
}
