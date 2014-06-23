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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.core.BlueSkyResolve;
import com.r.app.taobaoshua.bluesky.dao.SysParDao;
import com.r.app.taobaoshua.bluesky.dao.TaskDao;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskAddr;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.service.command.QueryCommand;
import com.r.core.exceptions.LoginErrorException;
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

	@Resource(name = "syaparDao")
	private SysParDao sysParDal;

	/** 设置当前链接的代理 */
	public void setSocketProxy(HttpProxy proxy) {
		taskDao.setSocketProxy(proxy);
	}

	// /---------------------系统参数------------//

	// /-------------------------网络-----------------------//

	/** 获取验证码 */
	public Image webGetLoginBlueSkyCaptchaImage() {
		return taskDao.getLoginCaptchaImage();
	}

	/**
	 * 
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
	 * @throws LoginErrorException
	 *             登陆错误时,抛出此错误
	 */
	public void webDoLogin(String account, String accountPassword, String captcha, String question, String answer) throws LoginErrorException {
		String login = taskDao.login(account, accountPassword, captcha, question, answer);
		if (0 < login.indexOf("alert")) { // 登陆错误
			String error = StringUtils.substringBetween(login, "alert('", "')");
			throw new LoginErrorException(error);
		}
		logger.debug(login);
	}

	/** 获取任务列表的html代码,只能取[1,10] */
	public Collection<Task> webGetTaskList(int page) {
		page = page < 1 ? 1 : page;
		page = 10 < page ? 10 : page;

		BlueSkyResolve resolve = BlueSky.getInstance().getResolve();
		List<Task> tasks = new ArrayList<Task>();
		String html = taskDao.getTaskListHtml(page, 2, 3);
		tasks.addAll(resolve.resolveTaskListHtml(html));
		return tasks;
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
			// 循环最新的任务
			// 判断此任务是否已经存在,存在则跳过,不存在则插入数据库中
			for (Task task : tasks) {
				Task findTask = queryByNumber(task.getNumber());
				if (findTask == null) {
					// 添加一些默认值
					task.setType(TaskAddr.淘宝任务区);
					task.setStatus(TaskStatus.等待接手);
					task.setIsUpdateTaskDetail(false);
					taskDao.create(task);
				}
			}
		}
	}

	/** 设置所有任务是否被检查过详细信息 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void setTaskDetailUpdated(boolean b) {
		if (b) {
			update("update Task set isUpdateTaskDetail = true");
		} else {
			update("update Task set isUpdateTaskDetail = false");
		}
	}

	/**
	 * 更新列表中任务的任务状态
	 * 
	 * @param task
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void updateTaskDetail(Task task) {
		if (task != null) {
			String taskDetail = taskDao.getTaskDetail(task.getTaskId());
			BlueSkyResolve resolve = BlueSky.getInstance().getResolve();
			resolve.resolveTaskDetail(task, taskDetail);
			task.setIsUpdateTaskDetail(true);
			taskDao.update(task);
		}
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

	/** 执行查询命令 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<Task> execQueryCommand(QueryCommand<Task> query) {
		return query.queryCollection(taskDao.getSession());
	}

	/** 接手任务 */
	public boolean acceptTask(Task task) {
		
		return false;
	}
}
