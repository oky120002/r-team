/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.service;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Order;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.core.BlueSkyResolve;
import com.r.app.taobaoshua.bluesky.dao.TaskDao;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enums.TaskStatus;
import com.r.core.httpsocket.context.HttpProxy;
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

	/** 设置当前链接的代理 */
	public void setSocketProxy(HttpProxy proxy) {
		taskDao.setSocketProxy(proxy);
	}

	// /-------------------------网络-----------------------//

	/** 获取验证码 */
	public Image getLoginBlueSkyCaptchaImage() {
		return taskDao.getLoginCaptchaImage();
	}

	/**
	 * 登陆
	 * 
	 * @param account
	 *            账户
	 * @param accountPassword
	 *            账户密码
	 * @param captcha
	 *            验证码
	 * @param question
	 *            密保问题
	 * @param answer
	 *            密保答案
	 */
	public void login(String account, String accountPassword, String captcha, String question, String answer) {
		taskDao.login(account, accountPassword, captcha, question, answer);
	}

	/** 获取任务列表的html代码,只能取[1,10] */
	public Collection<Task> getTaskList(int page) {
		page = page < 1 ? 1 : page;
		page = 10 < page ? 10 : page;

		BlueSkyResolve resolve = BlueSky.getInstance().getResolve();
		List<Task> tasks = new ArrayList<Task>();
		String html = taskDao.getTaskListHtml(page, 2, 3);
		tasks.addAll(resolve.resolveTaskListHtml(html));
		return tasks;
	}

	/** 获取任务详细信息 */
	public String getTaskDetail(String taskid) {
		return taskDao.getTaskDetail(taskid);
	}

	// /-------------------------数据-----------------------//

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

	/**
	 * 
	 * @param status
	 *            任务状态
	 * @param order
	 *            排序 1:发布点从高到低,其它:不排序
	 * @param firstResult
	 *            起始结果条数,-1则不设置起始条数
	 * @param maxResults
	 *            最大结果条数,-1则不设置最大条数
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<Task> query(TaskStatus status, int order, int firstResult, int maxResults) {
		Task task = new Task();
		if (status != null) {
			task.setStatus(status);
		}

		Order[] orders = new Order[3];
		if (order == 1) {
			orders[0] = Order.desc("publishingPoint");
		}

		return taskDao.queryByExample(task, firstResult, maxResults, orders);
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
	 * 根据任务编号查询任务实体
	 * 
	 * @param number
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public Task queryByNumber(String number) {
		Task task = new Task();
		task.setNumber(number);
		List<Task> tasks = taskDao.queryByExample(task);
		if (CollectionUtils.isEmpty(tasks)) {
			return null;
		} else {
			return tasks.get(0);
		}
	}

	/**
	 * 更新可接任务列表
	 * 
	 * @param tasks
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void updateTaskList(Collection<Task> tasks) {
		if (CollectionUtils.isNotEmpty(tasks)) {
			// FIXME r-app:taobaoshua 这里处理有问题,不能这样全部变更状态.一定要有个变更状态的条件才行
			// 把历史任务状态(未接手)全部更改为已接手
			String hql = MessageFormatter.format(" update Task set status = '{}' where status = '{}' ", TaskStatus.别人已经接手.name(), TaskStatus.未接手.name()).getMessage();
			taskDao.updateOrDeleteByHql(hql);

			// 循环最新的任务
			// 判断此任务是否已经存在,存在则更改任务状态.不存在则插入数据库中
			for (Task task : tasks) {
				Task findTask = queryByNumber(task.getNumber());
				if (findTask == null) {
					taskDao.create(task);
				} else {
					findTask.setStatus(TaskStatus.未接手);
					taskDao.update(findTask);
				}
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public long queryAllSize() {
		return taskDao.queryAllSize();
	}
}
