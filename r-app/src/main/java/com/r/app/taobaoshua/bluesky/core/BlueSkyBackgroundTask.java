/**
 * 
 */
package com.r.app.taobaoshua.bluesky.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.app.taobaoshua.bluesky.service.command.TaskQueryCommand;
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
					List<Task> tasks = new ArrayList<Task>();
					service.webGetTaskList(tasks, page);
					service.updateTaskList(tasks);
					TaskUtil.sleep(5_000);
				}
				logger.debug("自动获取任务信息........");
			}
		}, -1, 5 * 10 + 30, null, null);
	}

	public void startAutoUpdateTaskDetail() throws SchedulerException {
		final TaskService service = blueSky.getService();
		service.setTaskDetailUpdated(false);

		logger.debug("启动后台任务 : 自动更新任务详细信息........");
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				if (blueSky.isLogin()) {

					TaskQueryCommand query = new TaskQueryCommand();
					query.setIsUpdateTaskDetail(false);
					query.setOrder(2);
					query.setPage(0, 1);
					List<Task> list = service.execQueryCommand(query);
					if (CollectionUtils.isEmpty(list)) {
						service.setTaskDetailUpdated(true);
						return;
					}
					Task task = list.get(0);
					service.updateTaskDetail(task);
					logger.debug("自动更新任务详细信息 --- {} : {} : {}", task.getNumber(), task.getTaskerAccount(), task.getStatus().name());
				}
			}
		}, -1, 10, null, null);
	}

	/**
	 * 启动所有的后台任务
	 * 
	 * @throws SchedulerException
	 */
	public void startAll() throws SchedulerException {
		startAutoTaskListInfos();
		startAutoUpdateTaskDetail();
	}

}
