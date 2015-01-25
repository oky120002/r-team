/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.zhuangxiubang.service;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.app.zhuangxiubang.core.Resolve;
import com.r.app.zhuangxiubang.dao.TaskDao;
import com.r.app.zhuangxiubang.model.Task;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.obtain.HImageObtain;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 
 * @author rain
 */
@Service("taskService")
public class TaskService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class); // 日志

    @Resource(name = "resolve")
    private Resolve resolve;

    private String username;

    private String password;

    private boolean _isLogined = false;

    // 是否能使用打码平台,在初始化方法中.確定打碼平台能否使用
    private boolean isDama;

    public TaskService() {
        super();
        logger.info("TaskService Instance............................");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        isDama = false;
        username = "令狐冲288";
        password = "QINqinwo132513";
    }

    @Resource(name = "taskDao")
    private TaskDao taskDao;

    /**
     * web操作 - 获取任务列表的html代码
     * 
     * @param page
     *            任务列表页码
     */
    public List<Task> getWebGetTaskList() {
        List<Task> tasks = new ArrayList<Task>();
        String html = taskDao.getTaskListHtml();
        resolve.resolveTaskList(tasks, html);
        return tasks;
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
     * @param username
     *            账户
     * @param password
     *            密码
     * @return 错误信息(登陆成功返回null)
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<Task> query(int firstResult, int maxResults) {
        return taskDao.query(firstResult, maxResults, Order.asc("isReaded"), Order.desc("isBm"), Order.desc("bianhao"));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public String doLogin() {
        if (_isLogined) {
            return null;
        }
        _isLogined = true;
        String loginPageHtml = taskDao.getLoginPageHtml();
        String resolveLoginViewstate = resolve.resolveLoginViewstate(loginPageHtml);
        return taskDao.login(username, password, resolveLoginViewstate);
    }

    /** 应标 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public String yingbiao(List<Task> newTasks) {

        List<Task> errorTasks = new ArrayList<Task>();
        StringBuilder sb = new StringBuilder();
        for (Task task : newTasks) {
            String checkApplay = taskDao.checkApplay(task);
            switch (checkApplay) {
            case "1":
                sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":请升级成付费用户！\r\n");
                // task.setStatus("此标已应标");
                task.setIsBm(false);
                break;
            case "2":
                sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":此标已应标！\r\n");
                task.setStatus("此标已应标");
                task.setIsBm(false);
                break;
            case "7":
                sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":此标已预约！\r\n");
                task.setStatus("此标已预约");
                task.setIsBm(false);
                break;
            case "10":
                sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":此标是自己发布的标！\r\n");
                task.setStatus("自己的标");
                task.setIsBm(false);
                break;
            default:
                logger.debug("{}--:{}:----应标checkApplay: {}", task.getBianhao(), task.getLoupan(), checkApplay);
                String[] checkApplays = checkApplay.split(";");
                if (checkApplays.length == 3) {
                    if (checkApplays[1].equals(checkApplays[2])) {
                        sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":应标超额[" + checkApplays[2] + "/" + checkApplays[1] + "]!\r\n");
                        task.setStatus("应标超额[" + checkApplays[2] + "/" + checkApplays[1] + "]");
                        task.setIsBm(false);
                    } else {

                        switch (checkApplays[0]) {
                        case "0": // 成功
                            String msg = _yingbiao(task);
                            if (StringUtils.isNotBlank(msg)) {
                                sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":" + msg + "!\r\n");
                                errorTasks.add(task);
                                task.setStatus(msg);
                            } else {
                                sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":----自动完成应标投稿成功----!\r\n");
                                task.setStatus("投稿成功");
                                task.setIsBm(false);
                            }
                            break;
                        case "3":
                            sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":您没有权限应标!\r\n");
                            task.setStatus("没有权限应标");
                            task.setIsBm(false);
                            break;
                        default:
                            sb.append(task.getBianhao() + "--:" + task.getLoupan() + ":未知错误.联系该联系的人吧!\r\n");
                            task.setStatus("未知错误.联系该联系的人吧");
                            task.setIsBm(false);
                            break;
                        }
                    }

                }
            }
        }

        saveReaded(newTasks, true);
        saveReaded(errorTasks,false);

        return sb.toString();
    }

    /**
     * 应标
     * 
     * @param task
     * 
     * @return 返回null 表示應標成功应标
     */
    private String _yingbiao(Task task) {
        String[] autCodes = _getAutCode(task);
        switch (autCodes[1]) {
        case "7":
            logger.debug("{} - 验证码错误,重新应标", task.getLoupan());
            return "验证码错误,重新应标";
        case "13": // 验证码正确,接受任务
            logger.debug("{} - 验证码正确,开始接受任务", task.getLoupan());
            String msg = taskDao.acceptTask(task, autCodes[0]);
            logger.debug("{} - 接受任务返回值: {}", task.getLoupan(), msg);
            if (!"-1".equals(msg)) {
                String acceptTaskListHtml = taskDao.getAcceptTaskList(task);
                String caseNo = resolve.resolveAcceptTaskCaseNo(acceptTaskListHtml);
                String result = taskDao.acceptCase(task, caseNo);
                if ("0".equals(result)) {
                    return null;
                } else {
                    return "投标发生未知错误,请联系该联系的人!";
                }
            } else {
                return "接受任务发生未知错误,请联系该联系的人";
            }
        default:
            return "校验验证码,未知错误[错误编码: " + autCodes[1] + "]";
        }
    }

    /** 获取验证码 */
    private String[] _getAutCode(Task task) {
        String autCode = null;
        String verifycode = null;
        if (isDama) {// 使用打码平台
            // TODO 完成自动打码
        } else { // 不能使用
            verifycode = HAlert.showAuthCodeDialog(new HImageObtain() {
                @Override
                public Dimension getHImageSize() {
                    return new Dimension(200, 100);
                }

                @Override
                public Image getHImage() {
                    return taskDao.getAuthCodeImage();
                }
            });
            autCode = taskDao.checYZM(task, verifycode);
        }
        return new String[] { verifycode, autCode };
    }

    public Image getImage() {
        return taskDao.getAuthCodeImage();
    }
}
