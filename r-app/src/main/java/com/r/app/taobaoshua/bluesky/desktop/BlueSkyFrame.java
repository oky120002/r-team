/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.Dimension;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.core.CoreUtil;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

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
		setSize(new Dimension(1000, 680));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
		try {
			setIconImage(CoreUtil.getDefaultIco());
		} catch (Exception e) {
		}
	}

	private void initComponents() {
		setContentPane(blueSkyPanel);
	}

	private void initListeners() {

	}

	@Override
	public void loginFinsh() {
		BlueSky.getInstance().setLogin(true);
		setContentPane(mainPanel);
		validate();
	}

	@Override
	public void loginSkip() {
		BlueSky.getInstance().setLogin(false);
		setContentPane(mainPanel);
		validate();
	}
}
