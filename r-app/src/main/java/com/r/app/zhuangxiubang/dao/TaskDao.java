/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.zhuangxiubang.dao;

import java.util.List;

import com.r.app.core.support.AbstractDao;
import com.r.app.zhuangxiubang.model.Task;

/**
 * 用户Dao
 * 
 * @author rain
 */
public interface TaskDao extends AbstractDao<Task> {

    /**
     * 获取任务列表html
     * 
     * @return
     */
    String getTaskListHtml();

    /**
     * 根据编号查找
     * 
     * @param bianhao
     * @return
     */
    Task findByBianHao(String bianhao);

    /**
     * 根据此招标信息是否已经阅读来查询
     * 
     * @param b
     * @return
     */
    List<Task> findByReaded(boolean b);
}
