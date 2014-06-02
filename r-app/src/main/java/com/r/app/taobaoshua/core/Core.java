/**
 * 
 */
package com.r.app.taobaoshua.core;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.core.desktop.ctrl.impl.dialog.HInfoDialog;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.log.LoggerListener;

/**
 * @author oky
 * 
 */
public class Core implements TaoBaoShuaStartup, LoggerListener {
	private static final Logger logger = LoggerFactory.getLogger(Core.class);

	private static Core core;
	private boolean isRunning;
	private CoreManger coreManger;
	private CoreAction coreAction;
	private HInfoDialog infoDialog;

	public Core() {
		super();
		logger.debug("Core newInstance..........");
	}

	public static Core getInstance() {
		return Core.core;
	}

	public CoreAction getCoreAction() {
		return coreAction;
	}

	public CoreManger getCoreManger() {
		return coreManger;
	}

	public HInfoDialog getInfoDialog() {
		return infoDialog;
	}

	/** 打印一行信息 */
	public void println(String message) {
		infoDialog.printlnInfo(message);
	}

	@Override
	public void init() {
		Core.core = this;
		coreAction = new CoreAction();
		coreManger = new CoreManger();
		infoDialog = new HInfoDialog("淘宝刷信息窗口");
	}

	@Override
	public void startup() {
		isRunning = true;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public boolean warn(String message, Throwable error) {
		infoDialog.printlnInfo(message);
		return true;
	}

	@Override
	public boolean debug(String message, Throwable error) {
		infoDialog.printlnInfo(message);
		return true;
	}

	@Override
	public boolean info(String message, Throwable error) {
		infoDialog.printlnInfo(message);
		return true;
	}

	@Override
	public boolean error(String message, Throwable error) {
		infoDialog.printlnInfo(message);
		return true;
	}

}
