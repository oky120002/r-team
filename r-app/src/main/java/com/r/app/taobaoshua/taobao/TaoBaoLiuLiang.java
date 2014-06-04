/**
 * 
 */
package com.r.app.taobaoshua.taobao;

import org.quartz.SchedulerException;

import com.r.core.util.TaskUtil;

/**
 * @author oky
 * 
 */
public class TaoBaoLiuLiang {

	/**
	 * 启动自动刷流量功能
	 * 
	 * @throws SchedulerException
	 */
	public void startup() throws SchedulerException {
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				
			}
		}, -1, 3 * 60, null, null);
	}
}
