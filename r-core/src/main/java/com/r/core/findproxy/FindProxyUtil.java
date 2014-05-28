/**
 * 
 */
package com.r.core.findproxy;

import java.net.Proxy;

import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.httpsocket.context.responseheader.ResponseStatus;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * "自动查找代理"工具类
 * 
 * @author oky
 * 
 */
public class FindProxyUtil {
	private static final Logger logger = LoggerFactory.getLogger(FindProxyUtil.class);

	/**
	 * 检查代理是否可以使用
	 * 
	 * @param proxy
	 *            代理
	 * @param timeout
	 *            此代理超时时间,如果 <= 0则不判断链接超时时间
	 * @param url
	 *            代理测试地址
	 * @param contains
	 *            代理测试地址返回的body中包含的值,用来监测代理是否正常使用
	 * @return
	 */
	public static boolean checkPoxry(Proxy proxy, int timeout, String url, String contains) {
		if (proxy == null) {
			return false;
		}
		long startTime = 0;
		long endTime = 0;
		try {
			HttpProxy httpProxy = HttpProxy.newInstance(true, proxy);
			HttpSocket httpSocket = HttpSocket.newHttpSocket(true, httpProxy);
			startTime = System.currentTimeMillis();
			ResponseHeader send = httpSocket.send(url);
			endTime = System.currentTimeMillis();

			if (!ResponseStatus.s200.equals(send.getStatus())) {
				throw new NetworkIOReadErrorException("非正常返回");
			}

			if (0 > timeout && (timeout < endTime - startTime)) {
				logger.warn("代理 {} 连接超时[{}毫秒]。", proxy.toString(), timeout);
			} else {
				String bodyToString = send.bodyToString();
				if (bodyToString.contains(contains)) {
					logger.debug("代理 {} 链接成功!", proxy);
					return true;
				} else {
					logger.warn("代理 {} 连接超时[{}毫秒]。", proxy.toString(), endTime - startTime);
				}
			}
		} catch (Exception e) {
			logger.warn("代理 {} 连接测试失败 : " + e.getMessage(), proxy.toString());
		}
		return false;
	}
}
