package com.r.app.taobaoshua.bluesky.websource;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

public class BlueSkyManger {
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyManger.class); // 日志

	private HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
	private HttpSocket noCookHttpSocket = HttpSocket.newHttpSocket(false, null);

	public BlueSkyManger() {
		super();
		logger.debug("CoreManger newInstance  ................");
	}

	/** 设置当前链接的代理 */
	public void setSocketProxy(HttpProxy httpProxy) {
		this.httpSocket.setProxy(httpProxy);
		this.noCookHttpSocket.setProxy(httpProxy);
	}

	/** 获取验证码图片 */
	public Image getLoginCaptchaImage() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Accept", "image/webp,*/*;q=0.8");
		map.put("Accept-Encoding", "gzip,deflate");
		map.put("Accept-Language", "zh-CN,zh;q=0.8");
		map.put("Host", "www2.88sxy.com");
		map.put("Referer", "http://www2.88sxy.com/user/login/");
		map.put("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
		ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/plus/verifycode.asp?n=", map);
		return responseHeader.bodyToImage();
	}

	public void login(String account, String accountPassword, String captcha, String question, String answer) {
		
	}
}
