/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.zhuangxiubang.dao;

import java.awt.Image;
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

    /** 获取登陆页html */
    String getLoginPageHtml();

    /**
     * 登陆
     * 
     * @param username
     *            账号
     * @param password
     *            密码
     * @param viewstate
     *            登陆用关键字
     * 
     * @return 错误信息(登陆成功返回null)
     */
    String login(String username, String password, String viewstate);

    /** 查询当前任务应标状态 */
    String checkApplay(Task task);

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

    /** 返回验证码 */
    Image getAuthCodeImage();

    /** 校验验证码 */
    String checYZM(Task task, String authCode);

    /** 接受应标 */
    String acceptTask(Task task, String autCode);

    /** 获取任务对应的投稿 */
    String getAcceptTaskList(Task task);

    /** 投稿 */
    String acceptCase(Task task, String caseNo);
}
