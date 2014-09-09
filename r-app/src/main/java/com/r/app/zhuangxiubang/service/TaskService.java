/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.zhuangxiubang.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.app.zhuangxiubang.ZhuangXiuBangApp;
import com.r.app.zhuangxiubang.core.Resolve;
import com.r.app.zhuangxiubang.dao.TaskDao;
import com.r.app.zhuangxiubang.model.Task;
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

    /**
     * web操作 - 获取任务列表的html代码
     * 
     * @param tasks
     *            任务列表
     * @param page
     *            任务列表页码
     */
    public void webGetTaskList(Collection<Task> tasks) {
        Resolve resolve = ZhuangXiuBangApp.getInstance().getResolve();
        String html = taskDao.getTaskListHtml();
        resolve.resolveTaskList(tasks, html);
    }

    /**
     * 保存信息<br/>
     * 先查找信息(以编号来判断)是否存在,存在则修改,不存在则填写抓取时间后保存
     * 
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void save(Collection<Task> tasks) {
        if (CollectionUtils.isNotEmpty(tasks)) {
            for (Task task : tasks) {
                Task t = taskDao.findByBianHao(task.getBianhao());
                if (t == null) {
                    task.setCreateDate(new Date());
                    task.setIsReaded(false);
                    taskDao.create(task);
                } else {
                    t.setIsBm(task.getIsBm());
                    taskDao.update(t);
                }
            }
        }
    }

    /**
     * 查询未读的招标信息
     * 
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Task> queryByNotRead() {
        return taskDao.findByReaded(false);
    }

    /**
     * 设置是否已经阅读
     * 
     * @param tasks
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void saveReaded(Collection<Task> tasks, boolean idReaded) {
        
        if (CollectionUtils.isNotEmpty(tasks)) {
            for (Task task : tasks) {
                task.setIsReaded(idReaded);
                taskDao.update(task);
            }
        }
    }

    /**
     * 查询所有的招标信息
     * 
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<Task> query(int firstResult, int maxResults) {
        return taskDao.query(firstResult, maxResults, Order.asc("isReaded"), Order.desc("isBm"), Order.desc("bianhao"));
    }
}
