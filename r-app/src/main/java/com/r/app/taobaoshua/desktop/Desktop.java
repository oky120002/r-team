/**
 * 
 */
package com.r.app.taobaoshua.desktop;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.JFrame;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.model.PV;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * 桌面窗口管理器
 * 
 * @author oky
 * 
 */
public class Desktop extends HBaseFrame {
	private static final long serialVersionUID = 4683581855428200977L;
	private static final Logger logger = LoggerFactory.getLogger(Desktop.class);
	private static final TaobaoShuaApp app = TaobaoShuaApp.getInstance();
	private LoginDesktop loginDesktop = null;

	private Set<PV> curPVList = new ConcurrentSkipListSet<PV>(); // 当前未完成的任务

	public Desktop() {
		super();
		logger.info("Init Desktop.........");
		loginDesktop = new LoginDesktop(this, "登陆友保", true);
		loginDesktop.setVisible(true);
		initStyle();
		initComponent();
		refreshPVList();
	}

	/** 初始化窗口样式 */
	private void initStyle() {
		setTitle("淘宝刷......");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(600, 400));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
	}

	/** 初始化控件 */
	private void initComponent() {

	}

	/** 载入PV列表 */
	private void refreshPVList() {
		try {
			// 每30秒就获取一次PV列表
			// FIXME r-app:taobaoshua 每30秒获取一次列表修改成可以配置这个时间
			TaskUtil.executeScheduleTask(new Runnable() {
				private int curPage = 1;

				@Override
				public void run() {
					try {
						Set<PV> pvList = app.getAction().getPVList(curPage++);
						if (CollectionUtils.isEmpty(pvList)) {
							curPage = 1; // 重置页数到第一页
						} else {
							curPVList.addAll(pvList);
						}
					} catch (IOException e) {
						HAlert.showErrorTips("获取PV列表失败", Desktop.this, e);
					}
				}
			}, -1, 30, null, null);
		} catch (SchedulerException e) {
			HAlert.showErrorTips("自动获取PV列表功能失效,请手动获取同时联系开发者说明情况.", Desktop.this, e);
		}
	}

	public static void main(String[] args) throws SchedulerException {

	}
}
