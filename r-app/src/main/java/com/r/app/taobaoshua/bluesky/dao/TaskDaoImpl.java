/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.dao;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.r.app.core.support.AbstractDaoImpl;
import com.r.app.taobaoshua.bluesky.model.BuyAccount;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.HttpProxy;
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
    private static final String POST_ENCODE = "gb2312";

    private HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);

    public TaskDaoImpl() {
        super(Task.class);
        logger.info("TaskDaoImpl Instance............................");
        httpSocket.setTimeout(60_000); // 1分钟超时
    }

    @Override
    public Session getSession() {
        return super.getSession();
    }

    /** 设置当前链接的代理 */
    @Override
    public void setSocketProxy(HttpProxy httpProxy) {
        this.httpSocket.setProxy(httpProxy);
    }

    /** 获取验证码图片 */
    @Override
    public Image getLoginCaptchaImage() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Referer", "http://www2.88sxy.com/user/login/");
        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/plus/verifycode.asp?n=", null, map);
        return responseHeader.bodyToImage();
    }

    /**
     * 登陆蓝天店主网
     * 
     * @param account
     * @param accountPassword
     * @param captcha
     * @param question
     * @param answer
     */
    @Override
    public String login(String account, String accountPassword, String captcha, String question, String answer) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Referer", "http://www2.88sxy.com/user/login/");

        HttpPost post = new HttpPost(POST_ENCODE);
        post.add("Username", account);
        post.add("Password", accountPassword);
        post.add("Question", question);
        post.add("Answer", answer);
        post.add("Verifycode", captcha);
        post.add("u1", null);

        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/user/CheckUserLogin.asp", post, null, map);
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    /** 获取任务列表html */
    @Override
    public String getTaskListHtml(int page, int type, int order) {
        page = 10 < page ? 10 : page;
        page = page < 1 ? 1 : page;
        type = 14 < type ? 2 : type;
        type = type < 0 ? 2 : type;
        order = 4 < order ? 3 : order;
        order = order < 0 ? 3 : order;

        // 第三个数字 1:淘宝任务,2:拍拍任务
        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/task/?" + type + "-" + order + "-1-0-" + page);
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String getTaskDetail(String taskid) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Referer", "http://www2.88sxy.com/task/TaskJieShou.asp");
        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/task/TaskDetail.asp?ID=" + taskid, null, map);
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String acceptTask(Task task) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Referer", "http://www2.88sxy.com/task/");
        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/task/TaskJieShou.asp?Action=Jie&ID=" + task.getTaskId(), null, map);
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String discardTask(Task task) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Referer", "http://www2.88sxy.com/task/TaskJieShou.asp");
        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/task/TaskJieShou.asp?Action=Quit&ID=" + task.getTaskId(), null, map);
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String getTaoBaoAccount() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Referer", "http://www2.88sxy.com/task/BinDing.asp");
        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/task/BinDing.asp?2-0-0-0-0", null, map);
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String bindingBuyAccount(Task task, BuyAccount buyAccount) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Referer", "http://www2.88sxy.com/task/BinDingJieShouHao.asp?Action=BinDing&Tid=" + buyAccount.getTaobaoTid() + "&ID=" + task.getTaskId());
        HttpPost post = new HttpPost(POST_ENCODE);
        post.add("KS_ShopName", buyAccount.getBuyAccount());
        post.add("Save.x", "60");
        post.add("Save.y", "20");
        post.add("ID", task.getTaskId());
        post.add("Tid", buyAccount.getTaobaoTid());
        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/task/BinDingJieShouHao.asp?Action=DoSave", post, null, map);
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String checkItemAddr(Task task, String itemAddr) {
        String taskId = task.getTaskId();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Referer", "http://www2.88sxy.com/task/SearchLaiLuDetail.asp?UserType=2&ID=" + taskId);

        HttpPost post = new HttpPost(POST_ENCODE);
        post.add("ProductUrl", itemAddr);
        post.add("ChkProductUrl", "验证商品网址");
        post.add("Action", "Check");
        post.add("ID", task.getTaskId());
        post.add("TaskType", "淘宝");

        ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/task/SearchLaiLuDetail.asp?UserType=2&ID=" + taskId, post, null, map);
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String pay(Task task) {

        return null;
    }
}
