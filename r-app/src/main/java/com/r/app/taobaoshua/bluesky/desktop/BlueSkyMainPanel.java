/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.desktop.tablemodel.TaskListTableModel;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enums.TaskStatus;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;
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
		setLayout(new BorderLayout());
	}

	private void initComponents() {
		// PV和PV任务列表
		HBaseBox centerBox = HBaseBox.createVerticalBaseBox();
		taskListTableModel = new TaskListTableModel();
		taskListTable = new JTable(taskListTableModel);
		taskListTable.setDefaultRenderer(Image.class, new ImageCellRenderer()); // 支持渲染图片
		// taskListTable.add
		taskListTable.setFillsViewportHeight(true);
		taskListTable.setDragEnabled(false);
		taskListTable.doLayout();
		centerBox.add(new JScrollPane(taskListTable));
		add(centerBox, BorderLayout.CENTER);

		// 按钮区域
		HBaseBox buttomBox = HBaseBox.createHorizontalBaseBox();
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
				TaskService service = blueSky.getService();
				System.out.println(service.getTaskDetail("648521"));

				taskInfosButton.setEnabled(false);
				taskInfosButton.setText("获取中...");
				List<Task> tasks = service.query(TaskStatus.未接手, 1, 1, 50);
				taskListTableModel.setTasks(tasks);
				taskListTable.updateUI();
				FitTableColumns(taskListTable);
				taskInfosButton.setText("获取任务信息");
				taskInfosButton.setEnabled(true);
			}
		});
	}

	public void FitTableColumns(JTable myTable) {
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();

		Enumeration<?> columns = myTable.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) myTable.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(myTable, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + myTable.getIntercellSpacing().width);
		}
	}
}
