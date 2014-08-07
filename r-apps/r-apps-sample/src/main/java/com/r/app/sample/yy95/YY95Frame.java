package com.r.app.sample.yy95;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.HBaseScrollPane;
import com.r.core.desktop.ctrl.impl.panle.HInfoPanel;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.Cookie;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.support.KV;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;

/**
 * 淘宝刷 登陆窗口
 * 
 * @author oky
 * 
 */
public class YY95Frame extends HBaseFrame implements ActionListener {
    private static final long serialVersionUID = -7265881767861403989L;
    private static final String BODY_ENCODE = "utf-8";
    private static final Logger logger = LoggerFactory.getLogger(YY95Frame.class);
    private HInfoPanel text = new HInfoPanel();
    private JTextArea account = new JTextArea();
    private JButton btnstart = new JButton("开始");
    private static int sum = 0; // 成功投票数量
    private static int accoutnsum = 0; // 已经投票账户数量
    private static int sums = 0; // 总投票数量
    @SuppressWarnings("unchecked")
    private JComboBox<KV<String, String>> useridCombo = new JComboBox<KV<String, String>>(new KV[] { KV.kv("泡泡", "11581"), KV.kv("米豆", "12541") });

    public YY95Frame() {
        super("YY95活动投票器 - 注意换ip -       支持风筝,支持９３１        ９３１✦﹍     雨『闪耀筝团』");
        initStyle();
        initComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
        case "start":
            start();
            break;
        }
    }

    private void initStyle() {
        setSize(new Dimension(800, 600));// 设置窗口大小
        setResizable(false);
        setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        btnstart.setActionCommand("start");
        btnstart.addActionListener(this);

        HBaseBox top = HBaseBox.createHorizontalBaseBox(new JLabel("选择投票主播 : "), HBaseBox.EmptyHorizontal, useridCombo);
        add(top, BorderLayout.NORTH);

        HBaseBox b = HBaseBox.createVerticalBaseBox(new HBaseScrollPane(account), HBaseBox.EmptyVertical, new HBaseScrollPane(text));
        add(b, BorderLayout.CENTER);

        add(btnstart, BorderLayout.SOUTH);
    }

    // // 泡泡:11581
    // // 米豆:12541

    @SuppressWarnings("unchecked")
    private void start() {
        sum = 0;
        sums = 0;
        String[] accounts = account.getText().split("\n");
        final KV<String, String> userid = (KV<String, String>) useridCombo.getSelectedItem();
        int group = 0;
        for (final String string : accounts) {
            TaskUtil.executeSequenceTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        accoutnsum++;
                        String[] split = string.trim().split("----");
                        start(split[0], split[1], userid, "98527708", "2261184504");
                    } catch (Exception e) {
                        logger(e.toString());
                    }
                }
            }, String.valueOf(group % 5));
            group++;
        }
    }

    private String randomInt(int size) {
        return RandomUtil.randomString("123456789", size);
    }

    public void start(String user, String password, KV<String, String> userId, String sid, String subSid) {
        try {
            HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
            loginBy95(httpSocket, user, password);
            // loginByRoom(httpSocket, user, password, sid);
            toupiao(httpSocket, user, userId);
        } catch (Exception e) {
            logger("请输入验证码或者密码错误! - 成功[" + sum + "]次/总[" + sums + "]次 - 已投票账户号数量[" + accoutnsum + "] - " + user);
        }
    }

    // 进入频道
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

        // System.out.println();

        responseHeader = httpSocket.send(url);
        body = responseHeader.bodyToString(BODY_ENCODE);

        // System.out.println(body);
    }

    // 进入95
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

    void toupiao(HttpSocket httpSocket, String accountname, KV<String, String> userId) {
        // 投票
        String username = userId.getKey();
        String user = userId.getValue();
        String body = null;
        String time = null;
        String jquery = null;
        ResponseHeader responseHeader = null;

       
        time = String.valueOf(new Date().getTime());
        jquery = "jQuery1720715190" + randomInt(10);
        responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/vote?callback=" + jquery + "_" + time + "&userId=" + user + "&actId=33&_=" + time);
        body = responseHeader.bodyToString(BODY_ENCODE);
        sums++;
        if (0 < body.indexOf("投票成功")) {
            sum++;
            logger(" - 投票[" + username + "]第一次成功" + " - 成功[" + sum + "]次/总[" + sums + "]次 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
        } else {
            logger(" - 投票[" + username + "]第一次失败" + " - 成功[" + sum + "]次/总[" + sums + "]次 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
        }

       
        time = String.valueOf(new Date().getTime());
        jquery = "jQuery1720715190" + randomInt(10);
        responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/vote?callback=" + jquery + "_" + time + "&userId=" + user + "&actId=33&_=" + time);
        body = responseHeader.bodyToString(BODY_ENCODE);
        sums++;
        if (0 < body.indexOf("投票成功")) {
            sum++;
            logger(" - 投票[" + username + "]第二次成功" + " - 成功[" + sum + "]次/总[" + sums + "]次 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
        } else {
            logger(" - 投票[" + username + "]第二次失败" + " - 成功[" + sum + "]次/总[" + sums + "]次 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
        }

        time = String.valueOf(new Date().getTime());
        jquery = "jQuery1720715190" + randomInt(10);
        responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/vote?callback=" + jquery + "_" + time + "&userId=" + user + "&actId=33&_=" + time);
        body = responseHeader.bodyToString(BODY_ENCODE);
        sums++;
        if (0 < body.indexOf("投票成功")) {
            sum++;
            logger(" - 投票[" + username + "]第三次成功" + " - 成功[" + sum + "]次/总[" + sums + "]次 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
        } else {
            logger(" - 投票[" + username + "]第三次失败" + " - 成功[" + sum + "]次/总[" + sums + "]次 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
        }
    }

    private void logger(String str) {
        logger.info(str);
        text.printlnInfo(str);
    }
}
