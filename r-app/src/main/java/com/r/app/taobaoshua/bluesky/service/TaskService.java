/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.service;

import java.awt.Image;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.core.BlueSkyResolve;
import com.r.app.taobaoshua.bluesky.dao.BuyAccountDao;
import com.r.app.taobaoshua.bluesky.dao.TaskDao;
import com.r.app.taobaoshua.bluesky.model.BuyAccount;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskAddr;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.service.command.QueryCommand;
import com.r.core.callback.SuccessAndFailureCallBack;
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
    
    @Resource(name = "buyAccountDao")
    private BuyAccountDao buyAccountDao;
    
    /** 设置当前链接的代理 */
    public void setSocketProxy(HttpProxy proxy) {
        taskDao.setSocketProxy(proxy);
    }
    
    // /---------------------系统参数------------//
    
    // /-------------------------网络-----------------------//
    
    /** web操作 - 获取验证码 */
    public Image webGetLoginBlueSkyCaptchaImage() {
        return taskDao.getLoginCaptchaImage();
    }
    
    /**
     * 
     * web操作 - 登陆
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
    }
    
    /**
     * web操作 - 获取任务列表的html代码
     * 
     * @param tasks
     *            任务列表
     * @param page
     *            任务列表页码
     * @param type
     *            列表类型<br />
     *            0:默认类型<br />
     *            1:所有任务<br />
     *            2:等待接手<br />
     *            3:虚拟<br />
     *            4:实物<br />
     *            5:套餐<br />
     *            6:立即<br />
     *            7:30分<br />
     *            8:1天<br />
     *            9:2天<br />
     *            10:3天<br />
     *            11:4天<br />
     *            12:5天<br />
     *            13:6天<br />
     *            14:7天<br />
     * @param order
     *            搜索排序<br />
     *            0:默认排序<br />
     *            1:担保金从高到低<br />
     *            2:担保金从低到高<br />
     *            3:发布点从高到低<br />
     *            4:发布点从低到高<br />
     */
    public void webGetTaskList(Collection<Task> tasks, int page, int type, int order) {
        page = page < 1 ? 1 : page;
        page = 10 < page ? 10 : page;
        
        BlueSkyResolve resolve = BlueSky.getInstance().getResolve();
        String html = taskDao.getTaskListHtml(page, type, order);
        resolve.resolveTaskList(tasks, html);
    }
    
    /** web操作 - 接手任务 */
    public void webAcceptTask(Task task, SuccessAndFailureCallBack callback) {
        String html = taskDao.acceptTask(task);
        if (0 < html.indexOf("history.back(-1)")) { // 发生异常
            callback.failure(StringUtils.substringBetween(html, "alert('", "');history"), null);
        } else {
            callback.success("接手任务成功", null);
        }
    }
    
    /** web操作 - 退出任务 */
    public void webDiscardTask(Task task, SuccessAndFailureCallBack callback) {
        String html = taskDao.discardTask(task);
        if (0 < html.indexOf("退出任务成功")) { // 发生异常
            callback.success("退出任务成功", null);
        } else {
            callback.failure(StringUtils.substringBetween(html, "alert('", "');location"), null);
        }
    }
    
    /** web操作 - 任务绑定买号 */
    public void webDoBindingBuyAccount(Task task, BuyAccount buyAccount, SuccessAndFailureCallBack callback) {
        String html = taskDao.bindingBuyAccount(task, buyAccount);
        if (0 < html.indexOf("绑定买号成功")) { // 发生异常
            callback.success("绑定买号成功", null);
        } else {
            callback.failure(StringUtils.substringBetween(html, "alert('", "');location"), null);
        }
    }
    
    // 好评
    public void webDoGoodPraise(Task task, SuccessAndFailureCallBack callBack) {
        // String html = taskDao.doGoodPraise(task);
        // if (0 < html.indexOf("成功")) { // 发生异常
        // callback.success("退出任务成功", null);
        // } else {
        // callback.failure(StringUtils.substringBetween(html, "alert('",
        // "');location"), null);
        // }
    }
    
    // 满意度评价
    public void webDoGoodDegree(Task task, SuccessAndFailureCallBack callback) {
        // String html = taskDao.doGoodDegree(task);
        // if (0 < html.indexOf("评价成功")) { // 发生异常
        // callback.success("评价成功", null);
        // } else {
        // callback.failure(html, null);
        // }
    }
    
    /** web操作 - 返回绑定的买号 */
    public void webGetBuyAccount(Collection<BuyAccount> buys) {
        BlueSky blueSky = BlueSky.getInstance();
        if (!blueSky.isLogin()) {
            return;
        }
        blueSky.getResolve().resolveTaoBaoAccount(buys, taskDao.getTaoBaoAccount());
    }
    
    /**
     * 检测宝贝地址是否正确
     * 
     * @param task
     * @param itemAddr
     * @param successAndFailureCallBack
     */
    public void webDoCheckItemAddr(Task task, String itemAddr, SuccessAndFailureCallBack successAndFailureCallBack) {
        String html = taskDao.checkItemAddr(task, itemAddr);
        if (0 < html.indexOf("商品地址验证通过")) { // 发生异常
            successAndFailureCallBack.success("商品地址验证通过", null);
        } else {
            successAndFailureCallBack.failure(StringUtils.substringBetween(html, "alert('", "');history"), null);
        }
    }
    
    /**
     * 支付
      * @param task
      * @param successAndFailureCallBack [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void webDoPay(Task task, SuccessAndFailureCallBack successAndFailureCallBack) {
        String html = taskDao.pay(task);
        if (0 < html.indexOf("")) { // 发生异常
            successAndFailureCallBack.success("支付成功", null);
        } else {
            successAndFailureCallBack.failure(StringUtils.substringBetween(html, "alert('", "');history"), null);
        }
        
    }
    
    // /-------------------------数据----Task-------------------//
    
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
    public int updateOrDeleteByHql(String hql, Map<String, Object> pars) {
        return taskDao.updateOrDeleteByHql(hql, pars);
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
                Task t = findByNumber(task.getNumber());
                if (t == null) {
                    // 添加一些默认值
                    task.setType(TaskAddr.淘宝任务区);
                    task.setStatus(TaskStatus.等待接手);
                    task.setIsUpdateTaskDetail(false);
                    taskDao.create(task);
                } else {
                    if (!TaskStatus.等待接手.equals(t.getStatus())) {
                        t.setStatus(TaskStatus.等待接手);
                        taskDao.update(t);
                    }
                }
            }
        }
    }
    
    /**
     * 更新列表中任务的任务详细信息
     * 
     * @param task
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void updateTaskDetail(Task task) {
        if (task != null && StringUtils.isNotBlank(task.getTaskId())) {
            TaskStatus status = task.getStatus();
            String taskDetail = taskDao.getTaskDetail(task.getTaskId());
            BlueSky.getInstance().getResolve().resolveTaskDetail(task, taskDetail);
            task.setIsUpdateTaskDetail(true);
            if (TaskStatus.排除.equals(status)) {
                task.setStatus(TaskStatus.排除);
            }
            taskDao.update(task);
        }
    }
    
    /** 重置所有任务是否被检查过详细信息 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void resetTaskDetailUpdated() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", new TaskStatus[] { TaskStatus.等待发货, TaskStatus.等待审核, TaskStatus.等待接手, TaskStatus.等待接手_已暂停, TaskStatus.等待支付, TaskStatus.等待收货, TaskStatus.等待绑定买号, TaskStatus.未知状态 });
        map.put("isUpdateTaskDetail", false);
        updateOrDeleteByHql("update " + Task.class.getName() + " set isUpdateTaskDetail = :isUpdateTaskDetail where status in (:status)", map);
    }
    
    /** 删除失效的任务 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteTaskByFailure() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", new TaskStatus[] { TaskStatus.任务已删除, TaskStatus.已关闭, TaskStatus.已完成, TaskStatus.异常, TaskStatus.未知状态 });
        map.put("status2", TaskStatus.排除);
        map.put("time", new Date(new Date().getTime()));
        int i = updateOrDeleteByHql("delete " + Task.class.getName() + " where status in (:status) or ( status = :status2 and taskPublishingTime < :time )", map);
        logger.debug("删除失效任务 {} 个.....", i);
    }
    
    /**
     * 根据任务编号查询任务实体
     * 
     * @param number
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public Task findByNumber(String number) {
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
    
    // /-------------------------数据----BuyAccount-------------------//
    
    /**
     * 根据买号账号名查询任务实体
     * 
     * @param buyAccount
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public BuyAccount findByBuyAccount(String buyAccount) {
        BuyAccount buy = new BuyAccount();
        buy.setBuyAccount(buyAccount);
        List<BuyAccount> buys = buyAccountDao.queryByExample(buy);
        if (CollectionUtils.isEmpty(buys)) {
            return null;
        } else {
            return buys.get(0);
        }
    }
    
    /**
     * 更新买号信息
     * 
     * @param tasks
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void updateBuyAccount(Collection<BuyAccount> buys) {
        buyAccountDao.deleteAll();
        if (CollectionUtils.isNotEmpty(buys)) {
            for (BuyAccount buy : buys) {
                buyAccountDao.create(buy);
            }
        }
    }
    
    /**
     * 根据是否启用查询绑定的买号,并且按照 今日接任务数升序,本周接任务数升序,淘宝信誉值降序排序
     * 
     * @param isEnable
     *            null:查询全部|true:查询启用|false:查询禁用
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public Collection<BuyAccount> queryByEnable(Boolean isEnable) {
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(BuyAccount.class.getName()).append(' ');
        if (isEnable != null) {
            hql.append(" where isEnable = ").append(isEnable.booleanValue()).append(' ');
        }
        hql.append(" order by takeTaskByDay asc, takeTaskByWeek asc, buyPrestige asc ");
        return buyAccountDao.queryByHql(hql);
    }
    
    /**
     * 排除此任务,不在接手<br/>
     * 如果已经处于排除状态,则改为启用状态
     * 
     * @param task
     * @param successAndFailureCallBack
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void doPaichuTask(Task task, SuccessAndFailureCallBack successAndFailureCallBack) {
        try {
            TaskStatus status = task.getStatus();
            if (status != null) {
                switch (status) {
                    case 排除:
                        task.setStatus(TaskStatus.等待接手);
                        break;
                    case 等待接手:
                        task.setStatus(TaskStatus.排除);
                        break;
                    default:
                        successAndFailureCallBack.failure("排除/启用失败 : 只能排除\"" + TaskStatus.等待接手.name() + "\"的任务", null);
                        return;
                }
            } else {
                task.setStatus(TaskStatus.排除);
            }
            
            taskDao.update(task);
            successAndFailureCallBack.success("排除/启用成功!", null);
        } catch (Exception e) {
            successAndFailureCallBack.failure(e.getMessage(), e);
        }
    }
}
