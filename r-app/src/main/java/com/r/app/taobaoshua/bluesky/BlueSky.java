/**
 * 
 */
package com.r.app.taobaoshua.bluesky;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.app.taobaoshua.bluesky.desktop.BlueSkyDialog;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class BlueSky implements TaoBaoShuaStartup {
	private static final Logger logger = LoggerFactory.getLogger(BlueSky.class);

	private static BlueSky bluesky;
	private boolean isRunning;
	private BlueSkyManger blueSkyManger;
	private BlueSkyAction blueSkyAction;
	private BlueSkyDialog blueSkyDialog;

	public BlueSky() {
		super();
		logger.debug("BlueSky newInstance..........");
	}

	public static BlueSky getInstance() {
		return BlueSky.bluesky;
	}

	@Override
	public void init() {
		BlueSky.bluesky = this;
		blueSkyManger = new BlueSkyManger();
		blueSkyAction = new BlueSkyAction();
		blueSkyDialog = new BlueSkyDialog();
	}

	@Override
	public void startup() {
		isRunning = true;
		blueSkyDialog.setVisible(true);
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	public BlueSkyManger getBlueSkyManger() {
		return blueSkyManger;
	}

	public BlueSkyAction getBlueSkyAction() {
		return blueSkyAction;
	}

	public BlueSkyDialog getBlueSkyDialog() {
		return blueSkyDialog;
	}
}
