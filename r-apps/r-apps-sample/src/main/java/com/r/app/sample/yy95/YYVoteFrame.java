package com.r.app.sample.yy95;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.app.sample.vote.context.VoteContext;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.HBaseScrollPane;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.panle.HInfoPanel;
import com.r.core.exceptions.LoginErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.CalUtil;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;

/**
 * 淘宝刷 登陆窗口
 * 
 * @author oky
 * 
 */
public class YYVoteFrame extends HBaseFrame implements ActionListener {
    private static final long serialVersionUID = -7265881767861403989L;
    private static final String BODY_ENCODE = "utf-8";
    private static final Logger logger = LoggerFactory.getLogger(YYVoteFrame.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");

    private static final StrIntKV[] sudu = new StrIntKV[] { 
            StrIntKV.accountKV("┊      ➣ 　 最爱菇凉           每11~16秒1个账号         一般拨号,脚本能达到的速度              支持菇凉,支持931", 11, 16, null), //
            StrIntKV.accountKV("┊      ➣ 　 心情舒畅           每8~13秒1个账号          10M拨号,脚本能达到的速度               支持菇凉,支持931", 8, 13, null), //
            StrIntKV.accountKV("┊      ➣ 　 安心啦             每5~10秒1个账号          10M光纤,脚本能达到的速度               支持菇凉,支持931", 5, 10, null), //
//            StrIntKV.accountKV("┊      ➣ 　 瞪着你             每1~2秒1个账号          好吧.这个速度明显有点异常了            支持菇凉,支持931", 1, 2, null), //
//            StrIntKV.accountKV("┊      ➣ 　 画个圈圈诅咒你     3线程并行,无间隔      刘翔都没有这个快                       支持菇凉,支持931", -3, null, null), //
//            StrIntKV.accountKV("┊      ➣ 　 扎小人             5线程并行,无间隔      你造嘛,这个速度,已经突破天际了         支持菇凉,支持931", -5, null, null), //
    }; // 投票对象数组
    
    private static final StrIntKV[] sudu2 = new StrIntKV[] { 
        StrIntKV.accountKV("┊      ➣ 　 最爱菇凉           每11~16秒1个账号         一般拨号,脚本能达到的速度              支持菇凉,支持931", 11, 16, null), //
        StrIntKV.accountKV("┊      ➣ 　 心情舒畅           每8~13秒1个账号          10M拨号,脚本能达到的速度               支持菇凉,支持931", 8, 13, null), //
        StrIntKV.accountKV("┊      ➣ 　 安心啦             每5~10秒1个账号          10M光纤,脚本能达到的速度               支持菇凉,支持931", 5, 10, null), //
        StrIntKV.accountKV("┊      ➣ 　 瞪着你             每1~2秒1个账号           好吧.这个速度明显有点异常了            支持菇凉,支持931", 1, 2, null), //
        StrIntKV.accountKV("┊      ➣ 　 画个圈圈诅咒你     3线程并行,无间隔         刘翔都没有这个快                       支持菇凉,支持931", -3, null, null), //
        StrIntKV.accountKV("┊      ➣ 　 扎小人             5线程并行,无间隔         你造嘛,这个速度,已经突破天际了         支持菇凉,支持931", -5, null, null), //
    }; // 投票对象数组

    private HInfoPanel text = new HInfoPanel();
    private JTextArea account = new JTextArea();
    private JButton btnstart = new JButton("开始");
    private JButton btnpiaoshu = new JButton("查看票数");
    private static int accoutnsum = 0; // 已经投票账户数量
    private JComboBox<StrIntKV> suduCombo = new JComboBox<StrIntKV>(sudu); // 投票账号选择

    private JCheckBox gn = new JCheckBox("美丽大方漂亮的菇凉");
    private JCheckBox gnss = new JCheckBox("贤惠萌萌哒的老板娘");

    private static int round = -1; // 投票轮数
    private static Map<String, User> users = new HashMap<String, User>(); // 参赛选手资料

    public YYVoteFrame() {
        super("Y歌之王-女神季投票器(自动投票菇凉和老板娘) - 支持风筝,支持９３１ - ９３１✦﹍ 雨『闪耀筝团』『守护七烟』 - 七烟大房");
        int index = JOptionPane.showConfirmDialog(this, "你最爱菇凉吗?", "请问:", JOptionPane.YES_NO_OPTION);
        if (index != 0) { // 不喜欢
            JOptionPane.showMessageDialog(this, "你不爱菇凉.就走开吧.黑粉", "黑粉", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else { // 喜欢
            String inputValue = JOptionPane.showInputDialog("请输入菇凉工会的直播间id:");
            if (!"931".equals(inputValue)) { // 正确
                if ("0619".equals(inputValue)) { // 作弊码
//                    isZuobi = true;
                    suduCombo.setModel(new DefaultComboBoxModel<StrIntKV>(sudu2));
                } else {
                    JOptionPane.showMessageDialog(this, "你最爱菇凉.怎么会连菇凉工会直播间都不知道.请离开,黑粉", "黑粉", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        }

        HAlert.showTips("大爱菇凉,请放心使用此投票器", "大爱菇凉", this);

        initStyle();
        initComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
        case "start":
            suduCombo.setEnabled(false);
            start();
            break;
        case "btnpiaoshu":
            btnpiaoshu();
            break;
        }
    }

    private void initStyle() {
        setSize(new Dimension(900, 600));// 设置窗口大小
        setResizable(false);
        setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        btnstart.setActionCommand("start");
        btnstart.addActionListener(this);
        btnpiaoshu.setActionCommand("btnpiaoshu");
        btnpiaoshu.addActionListener(this);
        gn.setSelected(true);
        gn.setEnabled(false);
        gnss.setSelected(true);

        JLabel jLabel1 = new JLabel("七烟背后伟大的男人 - 雨");
        jLabel1.setForeground(Color.BLUE);
        JLabel jLabel2 = new JLabel("爱菇凉,支持菇凉,支持931,支持931主播    ");
        jLabel2.setForeground(Color.RED);

        HBaseBox one = HBaseBox.createHorizontalBaseBox(new JLabel("选择投票女神 : "), gn, gnss, HBaseBox.createHorizontalGlue());
        HBaseBox two = HBaseBox.createHorizontalBaseBox(new JLabel("投 票  速 度 : "), suduCombo);
        HBaseBox top = HBaseBox.createVerticalBaseBox(one, HBaseBox.EmptyVertical, two);
        add(top, BorderLayout.NORTH);

        HBaseBox b = HBaseBox.createVerticalBaseBox(new HBaseScrollPane(account), HBaseBox.EmptyVertical, new HBaseScrollPane(text));
        add(b, BorderLayout.CENTER);

        HBaseBox botton = HBaseBox.createHorizontalRight(jLabel1, HBaseBox.createHorizontalGlue(), jLabel2, btnpiaoshu, btnstart);
        add(botton, BorderLayout.SOUTH);

        text.printlnInfo("如果想查看各个主播票数,请在第一行填入正确的没有验证码的能登入的账号和密码(xxxx----xxxx)");
        text.printlnInfo("菇凉我们爱你");
        text.printlnInfo("菇凉我们爱你");
        text.printlnInfo("菇凉我们爱你");
        text.printlnInfo("柒烟 我宣你");
        text.printlnInfo("柒烟 我宣你");
        text.printlnInfo("柒烟 我宣你");
        
        
        VoteContext voteContext = VoteContext.getInstance();
        
        voteContext.randomVote()

    }

    // 进入95
    void loginYY(HttpSocket httpSocket, String user, String password) throws LoginErrorException {
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

        if (StringUtils.isBlank(url)) {
            throw new LoginErrorException("账号[" + user + "]登陆失败! (个别情况需要验证码或者密码错误 && 大批量登陆失败,请更换IP地址)");
        }

        responseHeader = httpSocket.send(url);
        body = responseHeader.bodyToString(BODY_ENCODE);
        url = StringUtils.substringBetween(body, "CookieWithCallBack(\"", "\",myCallBack");

        if (StringUtils.isBlank(url)) {
            throw new LoginErrorException("账号[" + user + "]登陆失败! (个别情况需要验证码或者密码错误 && 大批量登陆失败,请更换IP地址)");
        }

        responseHeader = httpSocket.send(url);
        body = responseHeader.bodyToString(BODY_ENCODE);

        baseUserInfo(httpSocket);
    }

    void baseUserInfo(HttpSocket httpSocket) {
        String body = null;
        ResponseHeader responseHeader = null;
        if (round == -1) { // 获取当前投票轮数,和参赛选手
            responseHeader = httpSocket.send("http://m.yy.com/s/hd/voice/js/vote.js");
            body = responseHeader.bodyToString(BODY_ENCODE);
            // 获取当前投票轮数
            round = Integer.valueOf(StringUtils.substringBetween(body, "\"round\":", ","));
            // 获取uid
            String[] uids = StringUtils.substringsBetween(body, "\"uid\":\"", "\",");
            for (String uid : uids) {
                users.put(uid, new User(uid));
            }

            responseHeader = httpSocket.send("http://m.yy.com/s/hd/voice/js/contestant.js");
            body = responseHeader.bodyToString(BODY_ENCODE);
            // 获取nickname
            String[] infos = StringUtils.substringsBetween(body, "\":{", "}");
            for (String info : infos) {
                String uid = StringUtils.substringBetween(info, "\"uid\" : \"", "\",");
                if (users.containsKey(uid)) {
                    users.get(uid).nickname = StringUtils.substringBetween(info, "\"nick\" : \"", "\",");
                }
            }
        }
    }

    private void start() {
        final String[] accounts = account.getText().split("\n");
        final StrIntKV sudu = (StrIntKV) suduCombo.getSelectedItem();

        switch (sudu.getValue()) {
        case -5:
        case -3:
            TaskUtil.executeTask(new Runnable() {
                @Override
                public void run() {
                    int abs = CalUtil.abs(sudu.getValue());
                    int group = 0;
                    for (final String string : accounts) {
                        TaskUtil.executeSequenceTask(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    accoutnsum++;
                                    String[] split = string.trim().split("----");
                                    btnToupiao(split[0], split[1]);
                                } catch (Exception e) {
                                    logger(e.toString());
                                }
                            }
                        }, String.valueOf(group % abs));
                        group++;
                    }
                }
            });
            break;
        default: // 大于0的.走下面.
            TaskUtil.executeTask(new Runnable() {
                @Override
                public void run() {
                    int group = 0;
                    for (final String string : accounts) {
                        TaskUtil.executeSequenceTask(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    accoutnsum++;
                                    String[] split = string.trim().split("----");
                                    btnToupiao(split[0], split[1]);
                                } catch (Exception e) {
                                    logger(e.toString());
                                }
                            }
                        }, String.valueOf(group % 5));
                        group++;
                        TaskUtil.sleep(sudu.getRandomMinAndMaxValue(1_000));
                    }
                }
            });
            break;
        }
    }

    /**
     * 查看票数
     */
    private void btnpiaoshu() {
        TaskUtil.executeTask(new Runnable() {
            @Override
            public void run() {
                HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
                httpSocket.setTimeout(60_000); // 1分钟超时

                String[] accounts = null;
                try {
                    accounts = account.getText().split("\n");
                    if (ArrayUtils.isEmpty(accounts)) {
                        text.printlnInfo("如果想查看各个主播票数,请在第一行填入正确的没有验证码的能登入的账号和密码(xxxx----xxxx)");
                        return;
                    }
                    if (accounts[0].indexOf("----") < 1) {
                        text.printlnInfo("如果想查看各个主播票数,请在第一行填入正确的没有验证码的能登入的账号和密码(xxxx----xxxx)");
                        return;
                    }
                } catch (Exception e) {
                    text.printlnInfo("如果想查看各个主播票数,请在第一行填入正确的没有验证码的能登入的账号和密码(xxxx----xxxx)");
                    return;
                }

                String account = accounts[0];
                String[] split = account.trim().split("----");
                try {
                    loginYY(httpSocket, split[0], split[1]); // 登陆
                } catch (LoginErrorException e) {
                    logger(e.getMessage());
                    return;
                }

                StringBuilder get = new StringBuilder();
                Collection<User> values = users.values();
                for (User user : values) {
                    get.append(user.uid).append(',');
                }

                ResponseHeader responseHeader = httpSocket.send("http://m.yy.com/act/activity/listAnchorVote.action?voteType=1&voteRound=15&uids=" + get.toString() + "&_=" + randomInt(13));
                String body = responseHeader.bodyToString();

                String[] infos = StringUtils.substringsBetween(body, "uid", "}");
                for (String info : infos) {
                    String uid = StringUtils.substringBetween(info, ":\"", "\"");
                    users.get(uid).piaoshu = StringUtils.substringBetween(info, "piaoshu\":\"", "\"");
                }

                text.printlnInfo("\r\n查询时间: " + sdf.format(new Date()));
                for (User user : values) {
                    text.printlnInfo(user.nickname + "\t : " + user.piaoshu + "票");
                }
                text.printlnInfo("------------------------------------------\r\n");
                btnpiaoshu.setEnabled(true);
            }
        });
    }

    // 投票
    private void btnToupiao(String user, String password) {
        try {
            HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
            httpSocket.setTimeout(60_000); // 1分钟超时
            try {
                loginYY(httpSocket, user, password);
            } catch (LoginErrorException e) {
                logger(e.getMessage());
                return;
            }
            toupiao(httpSocket, user);
        } catch (Exception e) {
            logger("请输入验证码或者密码错误! - 已投票账户号数量[" + accoutnsum + "] - " + user);
        }
    }

    private void toupiao(HttpSocket httpSocket, String accountname) {
        // 投票
        String body = null;
        ResponseHeader responseHeader = null;

        // 投票-菇凉
        responseHeader = httpSocket.send("http://m.yy.com/act/activity/addAnchorVote.action?voteType=1&voteRound=" + round + "&anUid=147125102&_=" + randomInt(13));
        body = responseHeader.bodyToString(BODY_ENCODE);
        if (0 < body.indexOf("\"result\":0")) {
            logger("投票[   菇凉   ]" + " - 成功 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
        } else if (0 < body.indexOf("\"result\":1")) {
            logger("投票[   菇凉   ]" + " - 失败 - 此帐号已经投过票 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
        } else {
            logger("投票[   菇凉   ]" + " - 失败 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
            logger.debug("菇凉 : body = " + body);
        }

        if (gnss.isSelected()) {
            // 投票-老板娘
            responseHeader = httpSocket.send("http://m.yy.com/act/activity/addAnchorVote.action?voteType=1&voteRound=" + round + "&anUid=432587555&_=" + randomInt(13));
            body = responseHeader.bodyToString(BODY_ENCODE);
            if (0 < body.indexOf("\"result\":0")) {
                logger("投票[  老板娘  ]" + " - 成功 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
            } else if (0 < body.indexOf("\"result\":1")) {
                logger("投票[   老板娘  ]" + " - 失败 - 此帐号已经投过票 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
            } else {
                logger("投票[  老板娘  ]" + " - 失败 - 已投票账户号数量[" + accoutnsum + "] - " + accountname);
                logger.debug("诗诗 : body = " + body);
            }
        }
    }

    private String randomInt(int size) {
        return RandomUtil.randomString("123456789", size);
    }

    private void logger(String str) {
        String time = sdf.format(new Date());
        logger.info(str);
        text.printlnInfo(time + " |- 支持菇凉,支持931 -+=  " + str);
    }

    private class User {
        public String uid;
        public String nickname;
        public String piaoshu;

        public User(String uid) {
            super();
            this.uid = uid;
        }
    }
}
