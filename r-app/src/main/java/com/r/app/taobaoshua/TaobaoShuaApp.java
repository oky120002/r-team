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
	private TaobaoShuaDesktop desktop;
	private YuuBoo yuuBoo;
	private TaoBao taoBao;

	private TaobaoShuaApp() {
		super();
		logger.info("TaobaoShuaApp newInstance  ................");
	}

	/** 返回此应用的唯一实例 */
	public static final TaobaoShuaApp getInstance() {
		return TaobaoShuaApp.app;
	}

	public YuuBoo getYuuBoo() {
		return this.yuuBoo;
	}

	public TaoBao getTaoBao() {
		return this.taoBao;
	}

	public TaobaoShuaDesktop getMainDesktop() {
		return desktop;
	}

	/** 返回众多代理中的下一个,出现过的代理不在返回 */
	public HttpProxy getNextProxy() {
		return HttpProxy.newInstance(true, Type.HTTP, "122.96.59.99", 82);
	}

	/** 启动 */
	public static void startup() {
		TaobaoShuaApp.app = new TaobaoShuaApp();
		TaobaoShuaApp.app.yuuBoo = new YuuBoo();
		TaobaoShuaApp.app.taoBao = new TaoBao();
		TaobaoShuaApp.app.desktop = new TaobaoShuaDesktop("淘宝刷.....");
		TaobaoShuaApp.app.getTaoBao().init();
		TaobaoShuaApp.app.getYuuBoo().init();
		app.getMainDesktop().setVisible(true);
	}

	public static void main(String[] args) {
		HCtrlUtil.setNoSpot();
		HCtrlUtil.setUIFont(null);
		HCtrlUtil.setWindowsStyleByWindows(null);
		TaobaoShuaApp.startup(); // 启动
	}

}
