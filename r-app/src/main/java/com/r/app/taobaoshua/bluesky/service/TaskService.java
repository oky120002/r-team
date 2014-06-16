/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.app.taobaoshua.bluesky.dao.TaskDao;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enums.TaskStatus;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 
 * @author rain
 */
@Service("taskService")
public class TaskService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class); // 日志

	public TaskService() {
		super();
		logger.info("TaskService Instance............................");
	}

	@Resource(name = "taskDao")
	private TaskDao taskDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void addTasks(Task... tasks) {
		taskDao.creates(tasks);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public int deleteAll() {
		return taskDao.deleteAll();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<Task> queryAll() {
		return taskDao.queryAll();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void update(Task task) {
		taskDao.update(task);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public int update(String hql) {
		return taskDao.updateOrDeleteByHql(hql);
	}

	/**
	 * 更新可接任务列表
	 * 
	 * @param tasks
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void updateTaskList(Collection<Task> tasks) {
		if (CollectionUtils.isNotEmpty(tasks)) {
			// 把历史任务状态(未接手)全部更改为已接手
			String hql = MessageFormatter.format(" update Task set status = '{}' where status = '{}' ", TaskStatus.别人已经接手.name(), TaskStatus.未接手.name()).getMessage();
			taskDao.updateOrDeleteByHql(hql);

			// 循环最新的任务
			// 判断此任务是否已经存在,存在则更改任务状态.不存在则插入数据库中
			for (Task task : tasks) {
				Task findTask = taskDao.find(task.getId());
				if (findTask == null) {
					taskDao.create(task);
				} else {
					findTask.setStatus(TaskStatus.未接手);
					taskDao.update(findTask);
				}
			}
		}
	}

}
