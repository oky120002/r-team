/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.zhuangxiubang.dao;

import java.awt.Image;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.r.app.core.support.AbstractDaoImpl;
import com.r.app.zhuangxiubang.model.Task;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.Cookie;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.HttpUrl;
import com.r.core.httpsocket.context.RequestHeader;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.RandomUtil;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("taskDao")
public class TaskDaoImpl extends AbstractDaoImpl<Task> implements TaskDao {
    private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class); // 日志
    private static final String BODY_ENCODE = "gb2312";

    @Resource(name = "httpSocket")
    private HttpSocket httpSocket;

    public TaskDaoImpl() {
        super(Task.class);
        logger.info("TaskDaoImpl Instance............................");
    }

    @Override
    public String getTaskListHtml() {
        ResponseHeader responseHeader = httpSocket.send("http://home.zz.fang.com/zhuangxiu/newtask_4_44_0_0_0_0_0_0_0__0_/");
        String taskHtml = responseHeader.bodyToString(BODY_ENCODE);
        httpSocket.send("http://home.zz.fang.com/zhuangxiu/login.aspx?_=142" + RandomUtil.randomString("1234567890", 10));

        Cookie global_cookie = Cookie.newCookieFromCookieStr("global_cookie", "1pams43qxhimeknqmgobyredr9oi5c97dmi");
        Cookie unique_cookie = Cookie.newCookieFromCookieStr("unique_cookie", "U_1pams43qxhimeknqmgobyredr9oi5c97dmi*2");
        httpSocket.addCookies(global_cookie, unique_cookie);
        return taskHtml;
    }

    @Override
    public String getLoginPageHtml() {
        ResponseHeader responseHeader = httpSocket.send("http://home.fang.com/User/login.aspx");
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String login(String username, String password, String viewstate) {
        HttpPost httpPost = new HttpPost(BODY_ENCODE);
        httpPost.add("__VIEWSTATE", viewstate);
        httpPost.add("loginstyle", "0");
        httpPost.add("username", username);
        httpPost.add("password", password);
        httpPost.add("txtmobile", "请输入注册的手机号");
        httpPost.add("txtcode", null);
        httpPost.add("btnLogin.x", "62");
        httpPost.add("btnLogin.y", "18");
        ResponseHeader responseHeader = httpSocket.send("http://home.fang.com/user/login.aspx", httpPost);

        responseHeader = httpSocket.send("http://home.fang.com/ideabook/ajax/LoginStateHandle.ashx");

        String body = responseHeader.bodyToString(BODY_ENCODE);

        if (StringUtils.equalsIgnoreCase(username, StringUtils.substringBetween(body, "soufunname\":\"", "\""))) {
            logger.debug("登陆成功({}).........", username);
            return null;
        }
        logger.debug("登陆失败({}).........", username);
        return "登陆错误. 联系该联系的人吧";
    }

    @Override
    public String checkApplay(Task task) {
        ResponseHeader responseHeader = httpSocket.send("http://home.zz.fang.com/zhuangxiu/action/CheckApplay.aspx?OpenStyle=0&0.2271637" + RandomUtil.randomString("0123456789", 10) + "&tcityID=44&taskid=" + task.getBianhao() + "&_=142" + RandomUtil.randomString("0123456789", 10));
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public Image getAuthCodeImage() {
        ResponseHeader responseHeader = httpSocket.send("http://home.zz.fang.com/zhuangxiu/CaptchaImg.aspx?r=2" + RandomUtil.randomString("123456789", 4));
        return responseHeader.bodyToImage();
    }

    @Override
    public String checYZM(Task task, String authCode) {
        ResponseHeader responseHeader = httpSocket.send("http://home.zz.fang.com/zhuangxiu/action/CheckApplay.aspx?action=yzm&code=" + authCode + "&taskid=" + task.getBianhao() + "&_=142" + RandomUtil.randomString("0123456789", 10));
        return responseHeader.bodyToString(BODY_ENCODE);
    }

//            GET /zhuangxiu/action/ApplyTask.aspx?taskid=121509&_=1422185493299&txtyzm=ffk6 HTTP/1.1
//            Host: home.zz.fang.com
//            User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0
//            Accept: */*
//            Accept-Language: zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3
//            Accept-Encoding: gzip, deflate
//            X-Requested-With: XMLHttpRequest
//            Referer: http://home.zz.fang.com/zhuangxiu/newtask_4_44_0_0_0_0_0_0_0__0_/
//            Cookie: jiatxShopWindow=1; __utma=147393320.335889423.1422179920.1422179920.1422179920.1; __utmb=147393320.20.10.1422179920; __utmc=147393320; __utmz=147393320.1422179920.1.1.utmcsr=home.zz.fang.com|utmccn=(referral)|utmcmd=referral|utmcct=/zhuangxiu/newtask/; global_cookie=1pams43qxhimeknqmgobyredr9oi5c97dmi; unique_cookie=U_1pams43qxhimeknqmgobyredr9oi5c97dmi*9; passport=usertype=1&userid=25397299&username=%c1%ee%ba%fc%b3%e5288&password=FB607EE0CEFAB7FFCE5581FD564FE923&isvalid=F4AFE7386E728B7361B6EDC6084FABAE&validation=F4AFE7386E728B73D4CC8DF9DABD12FCEF57D58BEEE0C4B2; sfut=B6906078D962B68E78725FBA0473803A1894F507AD97DABD94F5FFE4B4326B4E992E791CA0054612EDF05BFF928C603E615C2A9F281B505F184E3ECF65CB252B36439B257C5CBF2EB1CE52DEF52BFED8CED693E57C1EB35B3CB7AD93386284A5; homezxb=home_weike_userid=5658&home_weike_user_validation=09BDE549439D78761D677F808F2322B3
//            Connection: keep-alive

    @Override
    public String acceptTask(Task task, String autCode) {
        String url = "http://home.zz.fang.com/zhuangxiu/action/ApplyTask.aspx?taskid=" + task.getBianhao() + "&_=142" + RandomUtil.randomString("0123456789", 10) + "&txtyzm=" + autCode;
        
        Map<String, Cookie> cookies = httpSocket.getCookies();
        RequestHeader rh = RequestHeader.newRequestHeaderByGet(HttpUrl.newInstance(url), httpSocket.getProxy());
        rh.putHeader("X-Requested-With", "XMLHttpRequest");
        rh.putHeader("Referer", "http://home.zz.fang.com/zhuangxiu/newtask_4_44_0_0_0_0_0_0_0__0_/");
        
        HttpSocket hs = HttpSocket.newHttpSocket(true, null);
        hs.setDebug(true);
        hs.setCookies(cookies);
        hs.setRequestHeader(rh);
        
        ResponseHeader responseHeader = hs.send(rh);
        httpSocket.setCookies(hs.getCookies());
        
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String getAcceptTaskList(Task task) {
        ResponseHeader responseHeader = httpSocket.send("http://dianpu.fang.com/Task/AppTogaoList.aspx?taskid=" + task.getBianhao());
        return responseHeader.bodyToString(BODY_ENCODE);
    }

    @Override
    public String acceptCase(Task task, String caseNo) {
        ResponseHeader responseHeader = httpSocket.send("http://dianpu.fang.com/Task/action/SendCase.aspx?caseid=" + caseNo + "&taskid=" + task.getBianhao());
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
