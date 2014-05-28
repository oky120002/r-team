/**
 * 
 */
package com.r.app.taobaoshua.yuuboo;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.yuuboo.data.YuuBooDataContext;
import com.r.app.taobaoshua.yuuboo.desktop.YuuBooDesktop;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class YuuBoo {
	private static final Logger logger = LoggerFactory.getLogger(YuuBoo.class);

	private static YuuBoo yuuBoo = null;
	private YuuBooDesktop yuuBoodesktop = null;
	private YuuBooDataContext yuuBooDataContext = null;
	private YuuBooAction yuuBooAction = null;
	private YuuBooManger yuuBooManger = null;
	private YuuBooResolve yuuBooResolve = null;
	private HttpSocket yuuBooSocket = null;

	public YuuBoo() {
		super();
		yuuBooDataContext = new YuuBooDataContext();
		yuuBooAction = new YuuBooAction();
		yuuBooManger = new YuuBooManger();
		yuuBooResolve = new YuuBooResolve();
		yuuBooSocket = HttpSocket.newHttpSocket(true, null);
		YuuBoo.yuuBoo = this;
	}

	public static final YuuBoo getInstance() {
		return YuuBoo.yuuBoo;
	}

	public YuuBooDesktop getYuuBoodesktop() {
		return yuuBoodesktop;
	}

	public YuuBooDataContext getYuuBooDataContext() {
		return yuuBooDataContext;
	}

	public YuuBooAction getYuuBooAction() {
		return yuuBooAction;
	}

	public YuuBooManger getYuuBooManger() {
		return yuuBooManger;
	}

	public YuuBooResolve getYuuBooResolve() {
		return yuuBooResolve;
	}

	public HttpSocket getYuuBooSocket() {
		return yuuBooSocket;
	}

	public void start() {
		
	}
}
