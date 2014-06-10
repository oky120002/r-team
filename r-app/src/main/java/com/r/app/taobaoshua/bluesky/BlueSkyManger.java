package com.r.app.taobaoshua.bluesky;

import java.awt.Image;

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

	/** 获取ip地址 */
	public String getNetWorkIp() {
		return noCookHttpSocket.send("http://www.ip.cn").bodyToString();
	}

	/** 获取验证码图片 */
	public Image getLoginCaptchaImage() {
		ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/user/login/");
		responseHeader.bodyToString();
		responseHeader = httpSocket.send("http://www2.88sxy.com/plus/verifycode.asp?n=0." + System.currentTimeMillis());
		System.out.println(responseHeader.bodyToString());
		return responseHeader.bodyToImage();
	}
}
