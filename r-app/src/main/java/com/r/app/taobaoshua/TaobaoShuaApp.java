/**
 * 
 */
package com.r.app.taobaoshua;

import java.net.Proxy.Type;

import com.r.app.taobaoshua.taobao.TaoBao;
import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.desktop.ctrl.HCtrlUtil;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 淘宝刷
 * 
 * @author Administrator
 * 
 */
public class TaobaoShuaApp {
	private static final Logger logger = LoggerFactory.getLogger(TaobaoShuaApp.class);

	private static TaobaoShuaApp app = null; // 应用程序
	private TaobaoShuaDesktop desktop = null;

	// 组件
	private TaoBaoShuaStartup yuuBoo = new YuuBoo(); // 优保
	private TaoBaoShuaStartup taoBao = new TaoBao(); // 淘宝

	private TaobaoShuaApp() {
		super();
		logger.info("TaobaoShuaApp newInstance  ................");
	}

	/** 返回此应用的唯一实例 */
	public static final TaobaoShuaApp getInstance() {
		return TaobaoShuaApp.app;
	}

	public YuuBoo getYuuBoo() {
		return (YuuBoo) this.yuuBoo;
	}

	public TaoBao getTaoBao() {
		return (TaoBao) this.taoBao;
	}

	public TaobaoShuaDesktop getMainDesktop() {
		return desktop;
	}

	/** 启动 */
	private static void standup() {
		logger.info("Standup TaobaoShuaApp...........");
		TaobaoShuaApp.app = new TaobaoShuaApp();
		TaobaoShuaApp.app.desktop = new TaobaoShuaDesktop("淘宝刷...");
		TaobaoShuaApp.app.desktop.setVisible(true);
	}

	public static void main(String[] args) {
		HCtrlUtil.setNoSpot();
		HCtrlUtil.setUIFont(null);
		HCtrlUtil.setWindowsStyleByWindows(null);
		TaobaoShuaApp.standup(); // 启动
	}

	/** 返回众多代理中的下一个,出现过的代理不在返回 */
	public HttpProxy getNextProxy() {
		return HttpProxy.newInstance(true, Type.HTTP, "122.96.59.99", 82);
	}
}
