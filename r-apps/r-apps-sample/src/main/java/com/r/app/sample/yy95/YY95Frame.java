package com.r.app.sample.yy95;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;
import com.r.core.desktop.ctrl.HBaseScrollPane;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.panle.HInfoPanel;
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
public class YY95Frame extends HBaseFrame implements ActionListener {
	private static final long serialVersionUID = -7265881767861403989L;
	private static final String BODY_ENCODE = "utf-8";
	private static final Logger logger = LoggerFactory.getLogger(YY95Frame.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final StrIntKV[] kvs = new StrIntKV[] { 
			StrIntKV.accountKV("┊      ➣　　米　　　豆　　　❉　  ┊高冷帅气逗比豆         支持菇凉,支持931", 12541, "米豆"), //
			StrIntKV.accountKV("┊      ➣ 　 泡　　　泡 　　 ❉  　┊原创音乐人萌泡泡       支持菇凉,支持931", 11581, "泡泡"), //
			StrIntKV.accountKV("┊      ➣　　知　　　秋　　　❉　　┊粤语情歌邻家大叔       支持菇凉,支持931", 13105, "知秋"), //
			StrIntKV.accountKV("┊      ➣　　 ＡＬｉｎ　 　　❉　　┊星海学院的邓紫棋       支持菇凉,支持931", 12441, "alin"),//
			StrIntKV.accountKV("┊      ➣　  米      粒　　  ❉　　┊乖巧萝莉邻家乖女       支持菇凉,支持931", 11593, "米粒"),//
			StrIntKV.accountKV("┊      ➣　  胡  艾  彤　  　❉　　┊大叔不要跑原创者       支持菇凉,支持931", 12397, "彤彤"),//
			StrIntKV.accountKV("┊      ➣　　倾　　　城　　　❉　  ┊村里姑凉叫城锅         支持菇凉,支持931", 11589, "倾城"),//
			StrIntKV.accountKV("┊      ➣　　米　小　诺　　　❉　  ┊甜美乖巧萌妹纸         支持菇凉,支持931", 12345, "小诺"),//
			StrIntKV.accountKV("┊      ➣　  思  小  黛　  　❉　　┊古代穿越古筝美女       支持菇凉,支持931", 10597, "小黛"),//
			StrIntKV.accountKV("┊      ➣　　逍　　　瑶　　　❉　  ┊甜美声控系女生         支持菇凉,支持931", 11529, "逍遥"),//
			StrIntKV.accountKV("┊      ➣　　大  锅  饭　　　❉　  ┊专业舞蹈姐妹花         支持菇凉,支持931", 11945, "饭饭"),//
			StrIntKV.accountKV("┊      ➣　　丫　　　丫　　　❉　  ┊马栏山的女神丫         支持菇凉,支持931", 14497, "丫丫"),//
			StrIntKV.accountKV("┊      ➣    疯　小　兔　　　❉　  ┊温柔甜歌兔女郎         支持菇凉,支持931", 12497, "小兔"),//
			StrIntKV.accountKV("┊      ➣　　夕      夏　　　❉　  ┊来自西夏的夕夏         支持菇凉,支持931", 12073, "夕夏"),//
	}; // 投票对象数组

	private static final StrIntKV[] sudu = new StrIntKV[] { 
			StrIntKV.accountKV("┊      ➣ 　 瞪着你             每1秒1个账号          好吧.这个速度明显有点异常了            支持菇凉,支持931", 1, null), //
			StrIntKV.accountKV("┊      ➣ 　 超级安全           每10秒1个账号         一般网络,脚本能达到的速度              支持菇凉,支持931", 10, null), //
			StrIntKV.accountKV("┊      ➣ 　 安心啦             每5秒1个账号          10M光纤,脚本能达到的速度               支持菇凉,支持931", 5, null), //
			StrIntKV.accountKV("┊      ➣ 　 微笑哦             每3秒1个账号          电信内部网络,高手的脚本能达到的速度    支持菇凉,支持931", 3, null), //
			StrIntKV.accountKV("┊      ➣ 　 画个圈圈诅咒你     3线程并行,无间隔      刘翔都没有这个快                       支持菇凉,支持931", -3, null), //
			StrIntKV.accountKV("┊      ➣ 　 扎小人             5线程并行,无间隔      你造嘛,这个速度,已经突破天际了         支持菇凉,支持931", -5, null), //
	}; // 投票对象数组

	private HInfoPanel text = new HInfoPanel();
	private JTextArea account = new JTextArea();
	private JButton btnstart = new JButton("开始");
	private JButton btnpiaoshu = new JButton("查看票数");
	private static int sum = 0; // 成功投票数量
	private static int accoutnsum = 0; // 已经投票账户数量
	private static int sums = 0; // 总投票数量
	private JComboBox<StrIntKV> useridCombo = new JComboBox<StrIntKV>(kvs); // 投票账号选择
	private JComboBox<StrIntKV> suduCombo = new JComboBox<StrIntKV>(sudu); // 投票账号选择

	public YY95Frame() {
		super("YY95活动投票器 --- 注意换ip --- 支持风筝,支持９３１ --- 偶是七烟的大房.你们造吗? --- ９３１✦﹍     雨『闪耀筝团』");
		int index = JOptionPane.showConfirmDialog(this, "你最爱菇凉吗?", "请问:", JOptionPane.YES_NO_OPTION);
		if (index != 0) { // 不喜欢
			JOptionPane.showMessageDialog(this, "你不爱菇凉.就走开吧.黑粉", "黑粉", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} else { // 喜欢
			String inputValue = JOptionPane.showInputDialog("请输入菇凉工会的直播间id:");
			if (!"931".equals(inputValue)) { // 正确
				JOptionPane.showMessageDialog(this, "你最爱菇凉.怎么会连菇凉工会直播间都不知道.请离开,黑粉", "黑粉", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
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
			useridCombo.setEnabled(false);
			suduCombo.setEnabled(false);
			start();
			break;
		case "btnpiaoshu":
			btnpiaoshu();
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
				loginBy95(httpSocket, split[0], split[1]);
				String time = String.valueOf(new Date().getTime());
				String jquery = "jQuery1720715191" + randomInt(10);
				ResponseHeader responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/list?actId=33&&pageSize=25&channelId=931&callback=" + jquery + "_" + time + "&_=" + randomInt(13));
				String json = responseHeader.bodyToString();

				String[] datas = json.split("\"id\":");
				int size = datas.length;
				text.printlnInfo("\r\n查询时间: " + sdf.format(new Date()));
				for (int index = 1; index < size; index++) {
					printlnStr(datas[index]);
				}
				text.printlnInfo("------------------------------------------\r\n");
				btnpiaoshu.setEnabled(true);
			}

			private void printlnStr(String str) {
				int indexOf = str.indexOf(",");
				// String userid = str.substring(0, indexOf);

				indexOf = str.indexOf("nickName\":\"");
				str = str.substring(indexOf + "nickName\":\"".length());
				indexOf = str.indexOf("\",\"");
				String nickname = str.substring(0, indexOf);

				indexOf = str.indexOf("popularity\":");
				str = str.substring(indexOf + "popularity\":".length());
				indexOf = str.indexOf(",\"");
				String popularity = str.substring(0, indexOf);

				text.printlnInfo(nickname + " : " + popularity + "票");
			}
		});
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
		btnpiaoshu.setActionCommand("btnpiaoshu");
		btnpiaoshu.addActionListener(this);

		JLabel jLabel = new JLabel("爱菇凉,支持菇凉,支持931,支持931主播    ");
		jLabel.setForeground(Color.RED);

		HBaseBox one = HBaseBox.createHorizontalBaseBox(new JLabel("选择投票主播 : "), useridCombo);
		HBaseBox two = HBaseBox.createHorizontalBaseBox(new JLabel("投 票  速 度 : "), suduCombo);
		HBaseBox top = HBaseBox.createVerticalBaseBox(one, HBaseBox.EmptyVertical, two);
		add(top, BorderLayout.NORTH);

		HBaseBox b = HBaseBox.createVerticalBaseBox(new HBaseScrollPane(account), HBaseBox.EmptyVertical, new HBaseScrollPane(text));
		add(b, BorderLayout.CENTER);

		HBaseBox botton = HBaseBox.createHorizontalRight(jLabel, btnpiaoshu, btnstart);
		add(botton, BorderLayout.SOUTH);

		text.printlnInfo("如果想查看各个主播票数,请在第一行填入正确的没有验证码的能登入的账号和密码(xxxx----xxxx)");
		text.printlnInfo("柒烟 我宣你");
		text.printlnInfo("柒烟 我宣你");
		text.printlnInfo("柒烟 我宣你");

	}

	// // 泡泡:11581
	// // 米豆:12541
	private void start() {
		final String[] accounts = account.getText().split("\n");
		final StrIntKV userid = (StrIntKV) useridCombo.getSelectedItem();
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
									start(split[0], split[1], userid, "98527708", "2261184504");
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
		default:	// 大于0的.走下面.
			TaskUtil.executeTask(new Runnable() {
				@Override
				public void run() {
					int abs = CalUtil.abs(sudu.getValue());
					int group = 0;
					for (final String string : accounts) {
						TaskUtil.sleep(abs * 1_000);
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
			});
			break;
		}
	}

	private String randomInt(int size) {
		return RandomUtil.randomString("123456789", size);
	}

	public void start(String user, String password, StrIntKV userId, String sid, String subSid) {
		try {
			HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
			httpSocket.setTimeout(60_000); // 1分钟超时
			loginBy95(httpSocket, user, password);
			// loginByRoom(httpSocket, user, password, sid);
			toupiao(httpSocket, user, userId);
		} catch (Exception e) {
			logger("请输入验证码或者密码错误! - 成功[" + sum + "]次/总[" + sums + "]次 - 已投票账户号数量[" + accoutnsum + "] - " + user);
		}
	}

	// 进入95
	void loginBy95(HttpSocket httpSocket, String user, String password) {
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

	void toupiao(HttpSocket httpSocket, String accountname, StrIntKV userId) {
		// 投票
		String username = userId.getNickname();
		Integer user = userId.getValue();
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
		text.printlnInfo(" - 支持菇凉,支持931 -+=  " + str);
	}
}
