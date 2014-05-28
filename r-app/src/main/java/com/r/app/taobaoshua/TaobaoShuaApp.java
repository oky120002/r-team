/**
 * 
 */
package com.r.app.taobaoshua;

import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.desktop.ctrl.HCtrlUtil;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 淘宝刷友保来路流量
 * 
 * @author Administrator
 * 
 */
public class TaobaoShuaApp {
	private static final Logger logger = LoggerFactory.getLogger(TaobaoShuaApp.class);

	private static TaobaoShuaApp app = null; // 应用程序
	private MainDesktop mainDesktop = null;
	

	// 友保
	private YuuBoo yuuBoo = new YuuBoo(); // 优保

	private TaobaoShuaApp() {
		super();
		logger.info("Init TaobaoShuaApp........");
	}

	/** 返回此应用的唯一实例 */
	public static final TaobaoShuaApp getInstance() {
		return TaobaoShuaApp.app;
	}

	public YuuBoo getYuuBoo() {
		return this.yuuBoo;
	}

	public MainDesktop getMainDesktop() {
		return mainDesktop;
	}

	/** 启动 */
	private static void standup() {
		logger.info("Standup TaobaoShuaApp...........");
		TaobaoShuaApp.app = new TaobaoShuaApp();
		TaobaoShuaApp.app.mainDesktop = new MainDesktop("淘宝刷...");
		TaobaoShuaApp.app.mainDesktop.setVisible(true);
	}

	public static void main(String[] args) {
		HCtrlUtil.setNoSpot();
		HCtrlUtil.setUIFont(null);
		HCtrlUtil.setWindowsStyleByWindows(null);
		TaobaoShuaApp.standup(); // 启动
	}
}
