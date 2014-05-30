/**
 * 
 */
package com.r.app.taobaoshua.yuuboo;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.yuuboo.data.YuuBooDataContext;
import com.r.app.taobaoshua.yuuboo.desktop.YuuBooDesktop;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class YuuBoo implements TaoBaoShuaStartup {
	private static final Logger logger = LoggerFactory.getLogger(YuuBoo.class);

	private static YuuBoo yuuBoo;
	private boolean isRunning;
	private YuuBooDesktop yuuBoodesktop;
	private YuuBooDataContext yuuBooDataContext;
	private YuuBooAction yuuBooAction;
	private YuuBooManger yuuBooManger;
	private YuuBooResolve yuuBooResolve;

	public YuuBoo() {
		super();
		logger.info("YuuBoo newInstance................");
	}

	public static final YuuBoo getInstance() {
		return YuuBoo.yuuBoo;
	}

	public YuuBooDesktop getYuuBooDesktop() {
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

	@Override
	public void init() {
		YuuBoo.yuuBoo = this;
		yuuBooDataContext = new YuuBooDataContext();
		yuuBooAction = new YuuBooAction();
		yuuBooManger = new YuuBooManger();
		yuuBooResolve = new YuuBooResolve();
		yuuBoodesktop = new YuuBooDesktop(TaobaoShuaApp.getInstance().getMainDesktop());
	}

	@Override
	public void startup() {
		isRunning = true;
		yuuBoodesktop.setVisible(true);
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
}
