/**
 * 
 */
package com.r.app.taobaoshua.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

import org.quartz.SchedulerException;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.data.DataChangerListener;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.HBaseScrollPane;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 桌面窗口管理器
 * 
 * @author oky
 * 
 */
public class Desktop extends HBaseFrame implements DataChangerListener, ActionListener {
	private static final long serialVersionUID = 4683581855428200977L;
	private static final Logger logger = LoggerFactory.getLogger(Desktop.class);
	private static final String COMMAND_START_TASK = "COMMAND_START_TASK"; // 命令_启动刷任务命令
	private static final TaobaoShuaApp app = TaobaoShuaApp.getInstance();
	private LoginDesktop loginDesktop = null;
	private JButton startButton = new JButton("xxx");

	private JTable pvTable; // pvs列表
	private PVListTableModel pvTableModel; // pvs列表数据源

	private JTable pvQuestTable; // pvs列表
	private PVQuestListTableModel pvQuestTableModel; // pvs列表数据源

	public Desktop() {
		super();
		logger.info("Init Desktop.........");
		loginDesktop = new LoginDesktop(this, "登陆友保", true);
		loginDesktop.setVisible(true);
		app.getDataContext().addChangerListener(this);
		initStyle();
		initComponent();
		initListeners();
		startPVCommand();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if (COMMAND_START_TASK.equals(actionCommand)) { // 执行耍来路流量命令
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

	/** 初始化窗口样式 */
	private void initStyle() {
		setTitle("淘宝刷......");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(800, 600));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
	}

	/** 初始化控件 */
	private void initComponent() {
		// PV和PV任务列表
		HBaseBox centerBox = HBaseBox.createVerticalBaseBox();
		pvTableModel = new PVListTableModel(app.getDataContext());
		pvTable = new JTable(pvTableModel);
		pvTable.setPreferredScrollableViewportSize(new Dimension(600, 150));
		pvTable.setFillsViewportHeight(true);
		pvTable.setDragEnabled(false);
		pvTable.doLayout();
		HBaseBox pvTableBox = HBaseBox.createVerticalBaseBox();
		pvTableBox.setBorder(BorderFactory.createTitledBorder("PV列表"));
		pvTableBox.add(new HBaseScrollPane(pvTable));
		centerBox.add(pvTableBox);
		pvQuestTableModel = new PVQuestListTableModel(app.getDataContext());
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

		startButton.setEnabled(false);
		HBaseBox buttomBox = HBaseBox.createHorizontalBaseBox();
		buttomBox.add(HBaseBox.createHorizontalGlue());
		buttomBox.add(startButton);
		add(buttomBox, BorderLayout.SOUTH);
	}

	private void initListeners() {
		startButton.setActionCommand(COMMAND_START_TASK);
		startButton.addActionListener(this);
	}

	/** 启动pv列表命令 */
	private void startPVCommand() {
		try {
			app.getAction().startPVListCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动获取[PV]列表功能失效,请手动获取同时联系开发者说明情况.", Desktop.this, e);
		}

		try {
			app.getAction().startPVQuestListCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动获取[PV任务]列表功能失效,请手动获取同时联系开发者说明情况.", Desktop.this, e);
		}

		try {
			app.getAction().startAutoTakeTask();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动[接手PV]功能失效,请手动获取同时联系开发者说明情况.", Desktop.this, e);
		}

		try {
			app.getAction().startExecCommand();
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动[完成淘宝搜索]功能失效,请手动获取同时联系开发者说明情况.", Desktop.this, e);
		}

	}
}
