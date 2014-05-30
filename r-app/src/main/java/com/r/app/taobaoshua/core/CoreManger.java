package com.r.app.taobaoshua.core;

import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

public class CoreManger {
	private static final Logger logger = LoggerFactory.getLogger(CoreManger.class); // 日志

	private HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
	private HttpSocket noCookHttpSocket = HttpSocket.newHttpSocket(false, null);

	public CoreManger() {
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
}
