/**
 * 
 */
package com.r.app.taobaoshua.bluesky;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.app.taobaoshua.bluesky.desktop.BlueSkyDialog;
import com.r.app.taobaoshua.bluesky.websource.BlueSkyAction;
import com.r.app.taobaoshua.bluesky.websource.BlueSkyManger;
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
	private ApplicationContext applicationContext;

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
		applicationContext = new ClassPathXmlApplicationContext("spring.xml");
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

	public BlueSkyManger getManger() {
		return blueSkyManger;
	}

	public BlueSkyAction getAction() {
		return blueSkyAction;
	}

	public BlueSkyDialog getDialog() {
		return blueSkyDialog;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
