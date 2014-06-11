/**
 * 
 */
package com.r.app.taobaoshua.yuuboo;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.yuuboo.data.YuuBooDataContext;
import com.r.app.taobaoshua.yuuboo.desktop.YuuBooCaptchaDialog;
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
	private YuuBooCaptchaDialog yuuBooCaptcha;
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

	public YuuBooDesktop getDesktop() {
		return yuuBoodesktop;
	}

	public YuuBooDataContext getDataContext() {
		return yuuBooDataContext;
	}

	public YuuBooAction getAction() {
		return yuuBooAction;
	}

	public YuuBooManger getManger() {
		return yuuBooManger;
	}

	public YuuBooResolve getResolve() {
		return yuuBooResolve;
	}

	@Override
	public void init() {
		YuuBoo.yuuBoo = this;
		yuuBooDataContext = new YuuBooDataContext();
		yuuBooAction = new YuuBooAction();
		yuuBooManger = new YuuBooManger();
		yuuBooResolve = new YuuBooResolve();
		yuuBooCaptcha = new YuuBooCaptchaDialog();
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

	public String getCaptcha() {
		return yuuBooCaptcha.getCaptcha();
	}
}
