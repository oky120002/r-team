/**
 * 
 */
package com.r.app.taobaoshua.bluesky.websource;

import java.util.Collection;
import java.util.List;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * 后台任务
 * 
 * @author oky
 * 
 */
public class BlueSkyBackgroundTask {
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyBackgroundTask.class);
	private static final BlueSky blueSky = BlueSky.getInstance();

	/** 启动自动获取任务信息功能 */
	public void startAutoTaskListInfos() {
		logger.debug("启动后台任务 : 自动获取任务信息........");
		TaskUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				BlueSkyAction action = blueSky.getAction();
				TaskService service = blueSky.getService();
				Collection<Task> tasks = action.getTaskList();
				service.updateTaskList(tasks);
			}
		});
	}

	/** 启动所有的后台任务 */
	public void startAll() {
		startAutoTaskListInfos();
	}
}
