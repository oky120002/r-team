/**
 * 
 */
package com.r.app.taobaoshua.core;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 * 
 */
public class Core implements TaoBaoShuaStartup {
	private static final Logger logger = LoggerFactory.getLogger(Core.class);

	private static Core core;
	private boolean isRunning;
	private CoreManger coreManger;
	private CoreAction coreAction;

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

	@Override
	public void init() {
		Core.core = this;
		coreAction = new CoreAction();
		coreManger = new CoreManger();
	}

	@Override
	public void startup() {
		isRunning = true;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

}
