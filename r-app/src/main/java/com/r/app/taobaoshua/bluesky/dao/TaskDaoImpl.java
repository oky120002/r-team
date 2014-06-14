/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.dao;

import org.springframework.stereotype.Repository;

import com.r.app.taobaoshua.bluesky.core.AbstractDaoImpl;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("taskDao")
public class TaskDaoImpl extends AbstractDaoImpl<Task> implements TaskDao {

	private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class); // 日志

	public TaskDaoImpl() {
		super(Task.class);
		logger.info("TaskDaoImpl Instance............................");
	}
}
