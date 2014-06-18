/**
 * 
 */
package com.r.app.taobaoshua.bluesky.core;

import java.util.Collection;

import org.quartz.SchedulerException;

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

	/**
	 * 启动自动获取任务信息功能
	 * 
	 * @throws SchedulerException
	 */
	public void startAutoTaskListInfos() throws SchedulerException {
		logger.debug("启动后台任务 : 自动获取任务信息........");
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				TaskService service = blueSky.getService();
				for (int page = 1; page < 11; page++) {
					Collection<Task> tasks = service.getTaskList(page); // 就取前三页
					service.updateTaskList(tasks);
					TaskUtil.sleep(5_000);
				}

			}

		}, -1, 5 * 10 + 30, null, null);
	}

	/**
	 * 启动所有的后台任务
	 * 
	 * @throws SchedulerException
	 */
	public void startAll() throws SchedulerException {
		startAutoTaskListInfos();
	}
}
