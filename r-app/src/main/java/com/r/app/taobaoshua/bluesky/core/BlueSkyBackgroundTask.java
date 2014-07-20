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
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.service.TaskService;
import com.r.app.taobaoshua.bluesky.service.command.QueryCommand;
import com.r.app.taobaoshua.bluesky.service.command.TaskQueryCommand;
import com.r.core.callback.SuccessAndFailureCallBack;
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
	public void execAutoTaskListInfos() throws SchedulerException {
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
	public void execAutoUpdateTaskDetail() throws SchedulerException {
		if (blueSky.isLogin()) {
			final TaskService service = blueSky.getService();
			logger.debug("启动后台任务 : 自动更新任务详细信息........");
			TaskUtil.executeScheduleTask(new Runnable() {
				@Override
				public void run() {

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
						service.resetTaskDetailUpdated();
						return;
					}
					Task task = list.get(0);
					service.updateTaskDetail(task);
					logger.debug("自动更新任务详细信息 --- {} : {} : {}", task.getNumber(), task.getTaskerAccount(), task.getStatus().name());
				}
			}, -1, 10, null, null);
		}
	}

	/** 自动的循环接手任务,再30分钟超时前,把要过期的任务,自动退出任务,然后重新接手 */
	private void execAutoLoopAcceptTask() throws SchedulerException {
		if (blueSky.isLogin()) {
			logger.debug("启动后台任务 : 自动延续快过期的任务........");
			// 3分钟检查一次
			TaskUtil.executeScheduleTask(new Runnable() {
				@Override
				public void run() {
					final TaskQueryCommand command = new TaskQueryCommand();
					command.setStatus(TaskStatus.等待接手);
					command.setTaskerAccount(blueSky.getLoginAccount()); // 接手人
					final TaskService service = blueSky.getService();
					List<Task> tasks = service.execQueryCommand(command);

					if (CollectionUtils.isNotEmpty(tasks)) {
						for (final Task task : tasks) {
							if (task.isTaskerTimeout()) {
								// 先放弃任务
								service.webDiscardTask(task, new SuccessAndFailureCallBack() {
									@Override
									public void success(String success, Object object) {
										// 放弃任务成功后,瞬间重新接手任务
										service.webAcceptTask(task, new SuccessAndFailureCallBack() {
											@Override
											public void success(String success, Object object) {
												logger.info("重新接手任务成功 : {}", task.getNumber());
											}

											@Override
											public void failure(String error, Object object) {
												logger.warn("重新接手任务失败 : {} , {}", task.getNumber(), error);
												// 只有在放弃任务成功,而接手任务失败的情况下,任务的详细信息才会变化
												service.updateTaskDetail(task);
											}
										});
									}

									@Override
									public void failure(String error, Object object) {
										logger.warn("放弃任务失败 : {} , {}", task.getNumber(), error);
									}
								});
							}
						}
					}
				}
			}, -1, 180, null, null);
		}
	}

	/** 执行一次命令 */
	private void execOneCommand() {
		final TaskService service = blueSky.getService();
		TaskUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				if (blueSky.isLogin()) {
					logger.debug("启动后台任务 : 重置所有任务是否被检查过详细信息..........");
					service.resetTaskDetailUpdated();
				}
				logger.debug("启动后台任务 : 删除失效的任务..........");
				service.deleteTaskByFailure();
			}
		});
	}

	/**
	 * 启动所有的后台任务
	 * 
	 * @throws SchedulerException
	 */
	public void startAll() throws SchedulerException {
		execOneCommand();
		execAutoTaskListInfos();
		execAutoUpdateTaskDetail();
		execAutoLoopAcceptTask();
	}
}
