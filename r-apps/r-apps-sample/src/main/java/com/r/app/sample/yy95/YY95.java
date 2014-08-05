/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.sample.yy95;

import java.io.File;
import java.io.IOException;
import java.net.Proxy.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpPost;
import com.r.core.httpsocket.context.HttpProxy;
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
						yy.login(split[0], split[1], "12541");
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}, String.valueOf(group % 10));
			group++;
		}
	}

	private String randomInt(int size) {
		return "5" + RandomUtil.randomString("0123456789", size - 1);
	}

	public void login(String user, String password, String userId) {
		try {
			logger.info("投票账号 : " + user + " 投票账号数量 : " + zzz++ + " : 投票成功次数 : " + xxx);

			HttpProxy newInstance = HttpProxy.newInstance(true, Type.HTTP, "118.144.147.240", 18186);
			HttpSocket httpSocket = HttpSocket.newHttpSocket(true, newInstance);
			httpSocket.setTimeout(60_000); // 1分钟超时
			StringBuffer sb = new StringBuffer();

			String time = String.valueOf(new Date().getTime());
			String jquery = "jQuery1720715190" + randomInt(10);
			//
			sb.append("http://udb.duowan.com/oauth/proxy/noticeudbaccess.do?jsoncallback=").append(jquery);
			sb.append("_").append(time);
			sb.append("&callbackURL=http%3A%2F%2F95.yy.com%2F&_=").append(time);
			ResponseHeader responseHeader = httpSocket.send(sb.toString());
			String json = responseHeader.bodyToString();
			String token = StringUtils.substringBetween(json, "oauth_token=", "\"}");

			//
			Map<String, String> map = new HashMap<String, String>();
			map.put("Referer", "http://udb.duowan.com/authorize.do?oauth_token=" + token + "&rdm=0.060897358" + randomInt(8) + "&UIStyle=qlogin");

			HttpPost post = new HttpPost(BODY_ENCODE);
			post.add("username", user);
			post.add("showpwd", "密码");
			post.add("password", password);
			post.add("oauth_token", token);
			post.add("denyCallbackURL", "");
			post.add("securityCode", "");
			responseHeader = httpSocket.send("http://udb.duowan.com/oauth/server/login_q.do", post, map);
			String body = responseHeader.bodyToString(BODY_ENCODE);
			String url = StringUtils.substringBetween(body, "callbackURL = \"", "\";");
			url = url.replace("amp;", "");

			//
			responseHeader = httpSocket.send(url);
			body = responseHeader.bodyToString(BODY_ENCODE);
			url = StringUtils.substringBetween(body, "CookieWithCallBack(\"", "\",myCallBack");

			//
			responseHeader = httpSocket.send(url);
			body = responseHeader.bodyToString(BODY_ENCODE);

			//
			time = String.valueOf(new Date().getTime());
			jquery = "jQuery1720715190" + randomInt(10);
			responseHeader = httpSocket.send("http://api.activityboard.game.yy.com/signuser/list?actId=33&&pageSize=15&callback=" + jquery + "_" + time + "&_=" + time);
			body = responseHeader.bodyToString(BODY_ENCODE);
			// System.out.println(body);

			// 头片
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
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
