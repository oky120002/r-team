/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.sample.yy95;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.Cookie;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
public class YY95 {
    private static final Logger logger = LoggerFactory.getLogger(YY95.class); // 日志
    private static final String BODY_ENCODE = "utf-8";
    private static int zzz = 0;
    private static int xxx = 0;

    public YY95() {

    }

    public static void main(String[] args) throws IOException {
        final YY95 yy = new YY95();

        File file = new File("./yy/2.txt");

        // 泡泡:11581
        // 米豆:12541
        int group = 0;
        List<String> readLines = FileUtils.readLines(file);
        for (final String string : readLines) {
            TaskUtil.executeSequenceTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        String[] split = string.split("----");
                        yy.login(split[0], split[1], "12541", "98527708", "2261184504");
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }, String.valueOf(group % 10));
            group++;
        }

    }

    private String randomInt(int size) {
        return RandomUtil.randomString("123456789", size);
    }

    public void login(String user, String password, String userId, String sid, String subSid) {
        try {
            logger.info("投票账号 : " + user + " 投票账号数量 : " + zzz++ + " : 投票成功次数 : " + xxx);
            HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
            // loginBy95(httpSocket, user, password);
            loginByRoom(httpSocket, user, password, sid);
            // toupiao(httpSocket, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void loginByRoom(HttpSocket httpSocket, String user, String password, String sid) {
        //
        ResponseHeader responseHeader = httpSocket.send("http://yy.com//get-data/" + sid + "?type=yyscene&referer=http://www.yy.com/98527708/&_=" + randomInt(8));
        String body = responseHeader.bodyToString(BODY_ENCODE);

        //
        HttpPost post = new HttpPost(BODY_ENCODE);
        responseHeader = httpSocket.send("http://www.yy.com/index/wakeudblogin", post);
        body = responseHeader.bodyToString(BODY_ENCODE);
        String url = StringUtils.substringBetween(body, "url\":\"", "\"") + "&UIStyle=xqlogin&rdm=0." + randomInt(16);
        String ttokensec = StringUtils.substringBetween(body, "ttokensec\":\"", "\"");
        httpSocket.getCookies().put("udboauthtmptokensec", Cookie.newCookieFromCookieStr("udboauthtmptokensec", ttokensec));

        //
        responseHeader = httpSocket.send(url);
        body = responseHeader.bodyToString(BODY_ENCODE);
        url = StringUtils.substringBetween(body, "href=\"", "\"");
  
//        System.out.println();
        
        responseHeader = httpSocket.send(url);
        body = responseHeader.bodyToString(BODY_ENCODE);

//        System.out.println(body);
    }

    void loginBy95(HttpSocket httpSocket, String user, String password) {

        httpSocket.setTimeout(60_000); // 1分钟超时
        StringBuffer sb = new StringBuffer();

        String time = String.valueOf(new Date().getTime());
        String jquery = "jQuery1720715190" + randomInt(10);
        sb.append("http://udb.duowan.com/oauth/proxy/noticeudbaccess.do?jsoncallback=").append(jquery);
        sb.append("_").append(time);
        sb.append("&callbackURL=http%3A%2F%2F95.yy.com%2F&_=").append(time);
        ResponseHeader responseHeader = httpSocket.send(sb.toString());
        String json = responseHeader.bodyToString();
        String token = StringUtils.substringBetween(json, "oauth_token=", "\"}");

        HttpPost post = new HttpPost(BODY_ENCODE);
        post.add("username", user);
        post.add("showpwd", "密码");
        post.add("password", password);
        post.add("oauth_token", token);
        post.add("denyCallbackURL", "");
        post.add("securityCode", "");
        responseHeader = httpSocket.send("http://udb.duowan.com/oauth/server/login_q.do", post);
        String body = responseHeader.bodyToString(BODY_ENCODE);
        String url = StringUtils.substringBetween(body, "callbackURL = \"", "\";");
        url = url.replace("amp;", "");

        responseHeader = httpSocket.send(url);
        body = responseHeader.bodyToString(BODY_ENCODE);
        url = StringUtils.substringBetween(body, "CookieWithCallBack(\"", "\",myCallBack");

        responseHeader = httpSocket.send(url);
        body = responseHeader.bodyToString(BODY_ENCODE);
    }

    void toupiao(HttpSocket httpSocket, String userId) {
        // 投票
        String time = String.valueOf(new Date().getTime());
        String jquery = "jQuery1720715190" + randomInt(10);
        ResponseHeader responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/list?actId=33&&pageSize=15&callback=" + jquery + "_" + time + "&_=" + time);
        String body = responseHeader.bodyToString(BODY_ENCODE);
        // System.out.println(body);

        //
        time = String.valueOf(new Date().getTime());
        jquery = "jQuery1720715190" + randomInt(10);
        responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/vote?callback=" + jquery + "_" + time + "&userId=" + userId + "&actId=33&_=" + time);
        body = responseHeader.bodyToString(BODY_ENCODE);
        logger.info(userId + " - 投票1次 - " + body);
        if (0 < body.indexOf("投票成功")) {
            xxx++;
        }

        time = String.valueOf(new Date().getTime());
        jquery = "jQuery1720715190" + randomInt(10);
        responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/vote?callback=" + jquery + "_" + time + "&userId=" + userId + "&actId=33&_=" + time);
        body = responseHeader.bodyToString(BODY_ENCODE);
        logger.info(userId + " - 投票2次 - " + body);
        if (0 < body.indexOf("投票成功")) {
            xxx++;
        }

        time = String.valueOf(new Date().getTime());
        jquery = "jQuery1720715190" + randomInt(10);
        responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/vote?callback=" + jquery + "_" + time + "&userId=" + userId + "&actId=33&_=" + time);
        body = responseHeader.bodyToString(BODY_ENCODE);
        logger.info(userId + " - 投票3次 - " + body);
        if (0 < body.indexOf("投票成功")) {
            xxx++;
        }
    }

    void loginChannel() {
        // // 进入频道
        // String uid = httpSocket.getCookie("yyuid").getValue();
        // System.out.println("uid : " + uid);
        //
        // url = "http://yy.com//get-data/" + sid + "?subSid=" + subSid +
        // "&type=yyscene&referer=http://www.yy.com/" + sid + "/" + subSid +
        // "/&_=" + randomInt(8);
        // responseHeader = httpSocket.send(url);
        // body = responseHeader.bodyToString();
        //
        // String str = StringUtils.substringBetween(body, "pps", "}");
        // String wyy = StringUtils.substringBetween(body, "wyy\":\"",
        // "\"");
        // String pp = StringUtils.substringBetween(str, "ip\":\"", "\"");
        // String po = StringUtils.substringBetween(str, "ports\":[", "]");
        //
        // time = String.valueOf(new Date().getTime());
        // url =
        // "http://stat2.web.yy.com/c.gif?act=webyycoreload&rel=yyscene&ver=1.422&ref=http%3A%2F%2Fwww.yy.com%2F98527708%2F2230296738%2F&took="
        // + randomInt(3) + "&pp=" + pp + ":" + po +
        // "&ip=123.147.195.2&time=" + time + "&wyy=" + wyy + "&sid=" + sid
        // + "&sidsub=" + subSid;
        // responseHeader = httpSocket.send(url);
        // // System.out.println(url);
        //
        // time = String.valueOf(new Date().getTime());
        // url = "http://stat2.web.yy.com/c.gif?act=webyylogin&uid=" + uid +
        // "&rel=yyscene&ver=1.422&ref=http%3A%2F%2Fwww.yy.com%2F98527708%2F2230296738%2F&took="
        // + randomInt(3) + "&pp=" + pp + ":" + po +
        // "&ip=123.147.195.2&time=" + time + "&wyy=" + wyy + "&sid=" + sid
        // + "&sidsub=" + subSid;
        // responseHeader = httpSocket.send(url);
        // // System.out.println(url);
        //
        // time = String.valueOf(new Date().getTime());
        // url =
        // "http://stat2.web.yy.com/c.gif?act=webyyjoinchannel&rel=yyscene&took="
        // + randomInt(3) +
        // "&ver=1.422&ref=http%3A%2F%2Fwww.yy.com%2F98527708%2F2230296738%2F&tlt=0&uid=394509771&pp="
        // + pp + ":" + po + "&ip=123.147.195.2&time=" + time + "&wyy=" +
        // wyy + "&sid=" + sid + "&sidsub=" + subSid;
        // responseHeader = httpSocket.send(url);
        // // System.out.println(url);
        //
        // time = String.valueOf(new Date().getTime());
        // url =
        // "http://stat2.web.yy.com/c.gif?act=webyysceneload&rel=yyscene&ver=1.422&ref=http%3A%2F%2Fwww.yy.com%2F98527708%2F2230296738%2F&tlt=16777217&took="
        // + randomInt(3) + "&ip=123.147.195.2&time=" + time + "&wyy=" + wyy
        // + "&sid=" + sid + "&sidsub=" + subSid;
        // responseHeader = httpSocket.send(url);
        // // System.out.println(url);
        //
        // time = String.valueOf(new Date().getTime());
        // responseHeader =
        // httpSocket.send("http://stat2.web.yy.com/c.gif?act=webyyhb&rel=yyscene&uid="
        // + uid +
        // "&ver=1.422&ref=http%3A%2F%2Fwww.yy.com%2F98527708%2F2230296738%2F&tlt=16777217&ip=123.147.195.2&time="
        // + time + "&wyy=" + wyy + "&sid=" + sid + "&sidsub=" + subSid +
        // "&dr=0");
        // time = String.valueOf(new Date().getTime());
        // responseHeader =
        // httpSocket.send("http://stat2.web.yy.com/c.gif?act=webyyhb&rel=yyscene&uid="
        // + uid +
        // "&ver=1.422&ref=http%3A%2F%2Fwww.yy.com%2F98527708%2F2230296738%2F&tlt=16777217&ip=123.147.195.2&time="
        // + time + "&wyy=" + wyy + "&sid=" + sid + "&sidsub=" + subSid +
        // "&dr=1");

    }
}
