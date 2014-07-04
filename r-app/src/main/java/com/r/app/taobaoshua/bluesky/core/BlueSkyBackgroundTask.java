/**
 * 
 */
package com.r.app.taobaoshua.bluesky.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.quartz.SchedulerException;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.app.taobaoshua.bluesky.service.command.QueryCommand;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.RandomUtil;
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

				List<Task> tasks = new ArrayList<Task>();

				service.webGetTaskList(tasks, 1, 0, 3);
				service.webGetTaskList(tasks, 2, 0, 3);
				service.updateTaskList(tasks);
				tasks.clear();
				TaskUtil.sleep(RandomUtil.randomInteger(0, 2_000));
				service.webGetTaskList(tasks, 1, 8, 3);
				service.webGetTaskList(tasks, 2, 8, 3);
				service.updateTaskList(tasks);
				tasks.clear();
				TaskUtil.sleep(RandomUtil.randomInteger(0, 2_000));
				service.webGetTaskList(tasks, 1, 9, 3);
				service.webGetTaskList(tasks, 2, 9, 3);
				service.updateTaskList(tasks);
				tasks.clear();
				TaskUtil.sleep(RandomUtil.randomInteger(0, 2_000));
				service.webGetTaskList(tasks, 1, 10, 3);
				service.webGetTaskList(tasks, 2, 10, 3);
				service.updateTaskList(tasks);
				tasks.clear();
				TaskUtil.sleep(RandomUtil.randomInteger(0, 2_000));

				// 其它的
				for (int type = 0; type < 11; type++) {
					for (int page = 1; page < 6; page++) {
						service.webGetTaskList(tasks, page, type, 3);
						service.updateTaskList(tasks);
						tasks.clear();
						TaskUtil.sleep(RandomUtil.randomInteger(1_000, 4_000));
					}
				}

				// 4天,5天,6天,7天
				for (int type = 11; type < 15; type++) {
					service.webGetTaskList(tasks, 1, type, 3);
					service.updateTaskList(tasks);
					tasks.clear();
					TaskUtil.sleep(RandomUtil.randomInteger(1_000, 4_000));
				}

				logger.debug("自动获取任务信息........");
			}
		}, -1, 400, null, null);
	}

	/** 自动获取任务详细信息 */
	public void startAutoUpdateTaskDetail() throws SchedulerException {
		final TaskService service = blueSky.getService();

		TaskUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				service.setTaskDetailUpdated(false);
			}
		});

		logger.debug("启动后台任务 : 自动更新任务详细信息........");
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				if (blueSky.isLogin()) {
					List<Task> list = service.execQueryCommand(new QueryCommand<Task>() {
						@SuppressWarnings("unchecked")
						@Override
						public List<Task> queryCollection(Session session) {
							StringBuffer hql = new StringBuffer();
							hql.append(" from ").append(Task.class.getName()).append(' ');
							hql.append(" where isUpdateTaskDetail = :isUpdateTaskDetail and taskId is not null ");
							hql.append(" order by publishingPointOneDay desc, number asc ");
							Query createQuery = session.createQuery(hql.toString());
							createQuery.setParameter("isUpdateTaskDetail", false);
							createQuery.setFirstResult(0);
							createQuery.setMaxResults(1);
							return createQuery.list();
						}
					});

					if (CollectionUtils.isEmpty(list)) {
						service.setTaskDetailUpdated(false);
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
