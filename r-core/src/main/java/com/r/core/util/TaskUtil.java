/**
 * 描          述:  <核心模块>
 * 修  改   人:  rain
 * 修改时间:  2012-11-2
 * <修改描述:>
 */
package com.r.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 
 * 任务调度工具
 * 
 * @see java.util.concurrent
 * @author rain
 * @version [1.0, 2012-11-12]
 */
public abstract class TaskUtil {
	/** 执行调度字符串 */
	private static final String RunnableCall = "_RunnableCall";

	/** 全局任务 */
	private static final String GlobalTask = "_GLOBALTASK";

	private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	private static Map<String, ExecutorService> singleThreadExecutorMap = new HashMap<String, ExecutorService>();

	/**
	 * 添加调度任务
	 * 
	 * @param runnable
	 *            任务执行接口
	 * @param cron
	 *            Cron表达式<br/ >
	 *            秒 0-59 , - * /<br/ >
	 *            分 0-59 , - * /<br/ >
	 *            小时 0-23 , - * /<br/ >
	 *            日期 1-31 , - * ? / L W C<br/ >
	 *            月份 1-12 或者 JAN-DEC , - * /<br/ >
	 *            星期 1-7 或者 SUN-SAT , - * ? / L C #<br/ >
	 *            年（可选） 留空, 1970-2099 , - * /<br/ >
	 * @exception SchedulerException
	 *                添加调度或者执行调度失败时,抛出此异常
	 */
	public static void executeScheduleTask(Runnable runnable, String cron) throws SchedulerException {
		AssertUtil.isNotBlank("能传入的cron不能为空字符串", cron);
		AssertUtil.isNotNull("能传入的runnable不能为空", runnable);

		String jobName = String.valueOf(runnable.hashCode()); // 调度任务名称
		// 调度器
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		// 作业器
		JobDetail jobDetail = JobBuilder.newJob(RunnableJob.class).withIdentity(jobName, Scheduler.DEFAULT_GROUP).build();
		jobDetail.getJobDataMap().put(RunnableCall, runnable);

		// 触发器
		CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(jobName, Scheduler.DEFAULT_GROUP).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

		// 作业器和触发器设置到调度器中
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}

	/**
	 * 
	 * 添加调度任务
	 * 
	 * @param runnable
	 *            任务执行接口
	 * @param name
	 *            触发器名称
	 * @param repeatConunt
	 *            重复次数(-1为无限重复)
	 * @param repeatInterval
	 *            重复的间隔时间
	 * @param startTime
	 *            触发器开始执行时间
	 * @param endTime
	 *            触发器结束时间
	 * 
	 * @exception SchedulerException
	 *                添加调度失败时,抛出此异常
	 */
	public static void executeScheduleTask(Runnable runnable, int repeatConunt, int repeatInterval, Date startTime, Date endTime) throws SchedulerException {
		String jobName = String.valueOf(runnable.hashCode());
		repeatConunt = repeatConunt < 0 ? -1 : repeatConunt;
		repeatInterval = repeatInterval < 0 ? 0 : repeatInterval;
		// 调度器
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		// 作业器
		JobDetail jobDetail = JobBuilder.newJob(RunnableJob.class).withIdentity(jobName, Scheduler.DEFAULT_GROUP).build();
		jobDetail.getJobDataMap().put(RunnableCall, runnable);

		// // 触发器
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		triggerBuilder.withIdentity(jobName, Scheduler.DEFAULT_GROUP);
		if (startTime != null) {
			triggerBuilder.startAt(startTime); // 设置启动时间
		}
		if (endTime != null) {
			triggerBuilder.endAt(endTime); // 设置结束时间
		}
		triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(repeatInterval).withRepeatCount(repeatConunt));
		SimpleTrigger simpleTrigger = (SimpleTrigger) triggerBuilder.build();

		// 作业器和触发器设置到调度器中
		scheduler.scheduleJob(jobDetail, simpleTrigger);
		scheduler.start();
	}

	/**
	 * 
	 * 并发的执行异步任务<br />
	 * 每次都开启新线程来执行任务
	 * 
	 * @param runnable
	 *            任务执行接口
	 * 
	 * @author rain
	 */
	public static void executeTask(final Runnable runnable) {
		cachedThreadPool.execute(runnable);
	}

	/**
	 * 
	 * 顺序添加全局异步任务<br />
	 * 此任务会新开线程来执行,添加的任务会按照先后顺序来执行
	 * 
	 * @param runnable
	 *            任务执行接口
	 * 
	 * @author rain
	 */
	public static void executeSequenceTask(final Runnable runnable) {
		executeSequenceTask(runnable, TaskUtil.GlobalTask);
	}

	/**
	 * 
	 * 顺序添加异步任务<br />
	 * 会根据传入的group来分组任务,此任务会新开线程来执行<br />
	 * 添加的任务会按照分组和添加的先后顺序来执行,每组中的任务是有顺序的,不同组中的任务是没有顺序的
	 * 
	 * @param runnable
	 *            任务执行接口
	 * @param group
	 *            分组名称
	 * 
	 * @author rain
	 */
	public static void executeSequenceTask(final Runnable runnable, final String group) {
		if (!singleThreadExecutorMap.containsKey(group)) {
			singleThreadExecutorMap.put(group, Executors.newSingleThreadExecutor());
		}
		singleThreadExecutorMap.get(group).execute(runnable);
	}

	/** 执行Runnable接口的调度任务 */
	public static class RunnableJob implements Job {
		@Override
		public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
			Runnable runnable = (Runnable) jobExecutionContext.getJobDetail().getJobDataMap().get(RunnableCall);
			runnable.run();
		}
	}

	/** 线程休眠 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) throws SchedulerException {
		// new JNowTimeLabel("abcd");
		// new JNowTimeLabel("efgh");
		// TaskUtil.executeScheduleTask(new Runnable() {
		// @Override
		// public void run() {
		// System.out.println("abcd" + DateUtil.formatDate(null, null));
		// }
		// }, "0/1 * * * * ?");
		//
		// TaskUtil.executeScheduleTask(new Runnable() {
		// @Override
		// public void run() {
		// System.out.println("efgh" + DateUtil.formatDate(null, null));
		// }
		// }, "0/1 * * * * ?");

		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				// System.out.println("hijk" + DateUtil.formatDate(null, null));
			}
		}, 0, 1, new Date(), null);
	}
}
