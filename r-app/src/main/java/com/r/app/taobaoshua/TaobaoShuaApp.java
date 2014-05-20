/**
 * 
 */
package com.r.app.taobaoshua;

import com.r.app.taobaoshua.action.Action;
import com.r.app.taobaoshua.desktop.Desktop;
import com.r.core.desktop.ctrl.HCtrlInit;
import com.r.core.httpsocket.HttpSocket;
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
	private static final int TIMEOUT = 60 * 1000; // 1分钟

	private static TaobaoShuaApp app; // 应用程序

	private Action action; // 游戏执行器
	private HttpSocket youbaoSocket; // 针对友保的套接字
	private HttpSocket taobaoSocket; // 针对淘宝的套接字
	private Desktop desktop; // 桌面主体

	private TaobaoShuaApp() {
		super();
		logger.info("Init TaobaoShuaApp........");
	}

	/** 返回此应用的唯一实例 */
	public static final TaobaoShuaApp getInstance() {
		return TaobaoShuaApp.app;
	}

	/** 获得执行器 */
	public Action getAction() {
		return action;
	}

	/** 获得桌面窗口管理器 */
	public Desktop getDesktop() {
		return this.desktop;
	}

	/** 获得友保套接字 */
	public HttpSocket getYoubaoSocket() {
		return youbaoSocket;
	}

	/** 获得淘宝套接字 */
	public HttpSocket getTaobaoSocket() {
		return taobaoSocket;
	}

	/** 启动 */
	private void standup() {
		logger.info("Standup TaobaoShuaApp...........");
		this.desktop.setVisible(true);
	}

	public static void main(String[] args) {
		HCtrlInit.initNoSpot();
		HCtrlInit.initUIFont(null);
		HCtrlInit.initWindowsStyleByWindows(null);

		TaobaoShuaApp.app = new TaobaoShuaApp();
		TaobaoShuaApp.app.action = new Action();
		TaobaoShuaApp.app.youbaoSocket = HttpSocket.newHttpSocket(true, null);
		TaobaoShuaApp.app.youbaoSocket.setTimeout(TIMEOUT);
		TaobaoShuaApp.app.taobaoSocket = HttpSocket.newHttpSocket(true, null);
		TaobaoShuaApp.app.taobaoSocket.setTimeout(TIMEOUT);
		TaobaoShuaApp.app.desktop = new Desktop();

		TaobaoShuaApp.getInstance().standup(); // 启动
	}
}