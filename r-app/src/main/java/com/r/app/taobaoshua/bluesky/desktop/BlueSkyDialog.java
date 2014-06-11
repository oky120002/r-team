/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import java.awt.Dimension;

import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class BlueSkyDialog extends HBaseFrame implements BlueSkyLoginPanleListener{
	private static final long serialVersionUID = -2203297079049245614L;
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyDialog.class);
	
	private BlueSkyLoginPanle blueSkyPanel = null;

	public BlueSkyDialog() {
		super("蓝天数据平台分析系统...");
		logger.debug("BlueSkyDialog newInstance..........");
		blueSkyPanel = new BlueSkyLoginPanle(this);
		initStyle();
		initComponents();
		initListeners();
	}

	private void initStyle() {
		setSize(new Dimension(600, 480));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
	}

	private void initComponents() {
		setContentPane(blueSkyPanel);
	}

	private void initListeners() {

	}

	@Override
	public void loginFinsh() {
		blueSkyPanel.setVisible(false);
	}
}
