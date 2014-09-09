/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.r.app.core.util.ImageUtil;
import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.BuyAccount;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * @author oky
 * 
 */
public class BlueSkyFrame extends HBaseFrame implements BlueSkyLoginPanleListener {
	private static final long serialVersionUID = -2203297079049245614L;
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyFrame.class);

	private BlueSkyLoginPanle blueSkyPanel = new BlueSkyLoginPanle(this);
	private BlueSkyMainPanel mainPanel = new BlueSkyMainPanel();

	public BlueSkyFrame() {
		super("蓝天数据平台分析系统...");
		logger.debug("BlueSkyDialog newInstance..........");
		initStyle();
		initComponents();
		initListeners();
	}

	private void initStyle() {
		setSize(new Dimension(1100, 680));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
		try {
			setIconImage(ImageUtil.getDefaultIco());
		} catch (Exception e) {
		}
	}

	private void initComponents() {
		setContentPane(blueSkyPanel);
	}

	private void initListeners() {

	}

	@Override
	public void loginFinsh(String account) {
		final BlueSky blueSky = BlueSky.getInstance();
		blueSky.setLogin(true, account);
		setContentPane(mainPanel);
		validate();

		// 获取绑定的买号
		TaskUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				TaskService service = blueSky.getService();
				List<BuyAccount> buys = new ArrayList<BuyAccount>();
				service.webGetBuyAccount(buys);
				service.updateBuyAccount(buys);
				logger.debug("获取绑定的买号完成.......");
			}
		});
	}

	@Override
	public void loginSkip() {
		BlueSky.getInstance().setLogin(false, null);
		setContentPane(mainPanel);
		validate();
	}
}
