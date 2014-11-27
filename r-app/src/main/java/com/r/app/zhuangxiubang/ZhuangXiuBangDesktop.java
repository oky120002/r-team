/**
 * 
 */
package com.r.app.zhuangxiubang;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;

import com.r.app.core.util.ImageUtil;
import com.r.app.zhuangxiubang.bean.TaskListTableModel;
import com.r.app.zhuangxiubang.model.Task;
import com.r.app.zhuangxiubang.service.TaskService;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.support.TitleCellRenderer;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * 登陆窗口
 * 
 * @author Administrator
 * 
 */
public class ZhuangXiuBangDesktop extends HBaseFrame implements ActionListener {
    private static final long serialVersionUID = -957767869361688549L;
    private static final Logger logger = LoggerFactory.getLogger(ZhuangXiuBangDesktop.class);
    private static final String COMMAND_EXIT = "command_exit"; // 命令_退出
    private static final String COMMAND_START = "command_start"; // 命令_开始监控
    private static final ZhuangXiuBangApp app = ZhuangXiuBangApp.getInstance();

    private JButton startBtn = new JButton("开始监控");
    private JTable taskListTable; // 招标列表
    private TaskListTableModel taskListTableModel; // 招标列表数据源

    public ZhuangXiuBangDesktop(String title) {
        super(title);
        logger.info("ZhuangXiuBangDesktop newInstance .............");
        initStyle();
        initComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        switch (actionCommand) {
        case COMMAND_EXIT: // 退出
            System.exit(0);
            break;
        case COMMAND_START: // 开始监控
            start();
            break;
        }
    }

    /** 开始监控 */
    private void start() {
        HAlert.showTips("开始进行监听", "开始进行监听", this);
        setVisible(false);
        try {
            TaskUtil.executeScheduleTask(new Runnable() {
                private boolean isflg = true;

                @Override
                public void run() {
                    startBtn.setEnabled(false);
                    startBtn.setText("监控中...");
                    TaskService service = (TaskService) app.getApplicationContext().getBean("taskService");
                    List<Task> tasks = new ArrayList<Task>();
                    service.webGetTaskList(tasks);
                    service.save(tasks);
                    tasks = service.query(0, 30);
                    taskListTableModel.setTasks(tasks);
                    taskListTable.updateUI();

                    List<Task> newTasks = service.queryByNotRead();
                    // 发现新的招标信息 则强制弹出提示框
                    if (isflg && CollectionUtils.isNotEmpty(newTasks)) {
                        isflg = false;
                        StringBuilder sb = new StringBuilder();
                        for (Task task : newTasks) {
                            sb.append(task.getBianhao() + ":" + task.getLoupan()).append("\r\n");
                        }
                        sb.append("\r\n\r\n-----点击确认阅读-----\r\n\r\n");
                        // 提示
                        ZhuangXiuBangDesktop.this.setVisible(false);
                        ZhuangXiuBangDesktop.this.setAlwaysOnTop(true);
                        TaskUtil.sleep(1000);
                        ZhuangXiuBangDesktop.this.setAlwaysOnTop(false);
                        ZhuangXiuBangDesktop.this.setVisible(true);
                        int yesno = JOptionPane.showConfirmDialog(ZhuangXiuBangDesktop.this, sb.toString(), "有新的招标信息,请阅读", JOptionPane.YES_NO_OPTION);
                        if (yesno == 0) { // 点击确认
                            service.saveReaded(newTasks, true);
                            tasks = service.query(0, 30);
                            taskListTableModel.setTasks(tasks);
                            taskListTable.updateUI();
                        } else {
                            ZhuangXiuBangDesktop.this.setVisible(false);
                        }
                        isflg = true;
                    }
                }
            }, -1, 20, null, null);
        } catch (SchedulerException e) {
            HAlert.showErrorTips(e.toString(), null, e);
        }
    }

    private void initStyle() {
        setSize(new Dimension(800, 600));// 设置窗口大小
        setResizable(false);
        setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
        try {
            setIconImage(ImageUtil.getDefaultIco());
        } catch (Exception e) {
        }
    }

    private void initComponents() {
        startBtn.addActionListener(this);
        startBtn.setActionCommand(COMMAND_START);

        HBaseBox southBox = HBaseBox.createHorizontalBaseBox();
        southBox.setBorder(BorderFactory.createTitledBorder("功能区")); // 设置箱子组件内边距
        southBox.add(HBaseBox.createHorizontalGlue());
        southBox.add(startBtn);
        add(southBox, BorderLayout.SOUTH);

        // PV和PV任务列表
        HBaseBox centerBox = HBaseBox.createVerticalBaseBox();
        taskListTableModel = new TaskListTableModel();
        taskListTable = new JTable(taskListTableModel);
        taskListTable.setDefaultRenderer(String.class, new TitleCellRenderer()); // 支持Title提示
        taskListTable.setFillsViewportHeight(true);
        taskListTable.setDragEnabled(false);
        taskListTableModel.setColWidth(taskListTable);
        taskListTable.doLayout();
        centerBox.add(new JScrollPane(taskListTable));
        add(centerBox, BorderLayout.CENTER);

    }
}
