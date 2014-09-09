/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.zhuangxiubang.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.r.app.core.support.AbstractDaoImpl;
import com.r.app.zhuangxiubang.model.Task;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.ResponseHeader;
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
    private static final String BODY_ENCODE = "gb2312";

    private HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);

    public TaskDaoImpl() {
        super(Task.class);
        logger.info("TaskDaoImpl Instance............................");
        httpSocket.setTimeout(60_000); // 1分钟超时
    }

    /** 获取任务列表html */
    @Override
    public String getTaskListHtml() {
        ResponseHeader responseHeader = httpSocket.send("http://home.zz.fang.com/zhuangxiu/newtask/");
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public Task findByBianHao(String bianhao) {
        Task task = new Task();
        task.setBianhao(bianhao);
        List<Task> queryByExample = queryByExample(task);
        if (CollectionUtils.isEmpty(queryByExample)) {
            return null;
        } else {
            return queryByExample.get(0);
        }
    }

    @Override
    public List<Task> findByReaded(boolean b) {
        Task task = new Task();
        task.setIsReaded(b);
        task.setIsBm(true);
        return queryByExample(task);
    }
}
