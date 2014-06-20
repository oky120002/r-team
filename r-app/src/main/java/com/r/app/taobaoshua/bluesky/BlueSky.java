/**
 * 
 */
package com.r.app.taobaoshua.bluesky;

import java.awt.Image;

import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.r.app.taobaoshua.TaoBaoShuaStartup;
import com.r.app.taobaoshua.bluesky.core.BlueSkyBackgroundTask;
import com.r.app.taobaoshua.bluesky.core.BlueSkyResolve;
import com.r.app.taobaoshua.bluesky.desktop.BlueSkyDialog;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.core.exceptions.io.IOReadErrorException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.ImageCacheUtil;
import com.r.core.util.ImageCacheUtil.ReadImage;
import com.r.core.util.ImageUtil;

/**
 * @author oky
 * 
 */
public class BlueSky implements TaoBaoShuaStartup {
	private static final Logger logger = LoggerFactory.getLogger(BlueSky.class);
	private static final String IMAGE_CACHE_KEY = BlueSky.class.getName();

	private static BlueSky bluesky;
	private boolean isRunning;
	private boolean isLogin;
	private BlueSkyResolve blueSkyResolve;
	private BlueSkyDialog blueSkyDialog;
	private BlueSkyBackgroundTask backgroundTask; // 后台任务
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
		
		// 实现图片缓存接口
		ImageCacheUtil.init(IMAGE_CACHE_KEY, new ReadImage() {
			@Override
			public Image readImage(String imageKey) throws IOReadErrorException {
				return ImageUtil.readImageFromIO("com/r/app/taobaoshua/bluesky/image/" + imageKey);
			}
		});
		
		applicationContext = new ClassPathXmlApplicationContext("spring.xml");
		backgroundTask = new BlueSkyBackgroundTask();
		blueSkyResolve = new BlueSkyResolve();
		blueSkyDialog = new BlueSkyDialog();
	}

	@Override
	public void startup() {
		if (!isRunning) {
			try {
				backgroundTask.startAll();
			} catch (SchedulerException e) {
				logger.warn("后台计划任务启动失败", e);
			}
		}
		blueSkyDialog.setVisible(true);
		isRunning = true;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	/** 是否已经登陆进入蓝天店主网 */
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public BlueSkyResolve getResolve() {
		return blueSkyResolve;
	}

	public BlueSkyDialog getDialog() {
		return blueSkyDialog;
	}

	public TaskService getService() {
		return applicationContext.getBean(TaskService.class);
	}

	/** 获取图片 */
	public Image getImage(String imageName) {
		return ImageCacheUtil.getImage(IMAGE_CACHE_KEY, imageName);
	}
}
