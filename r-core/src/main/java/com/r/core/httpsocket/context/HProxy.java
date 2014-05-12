/**
 * 
 */
package com.r.core.httpsocket.context;

import java.net.Proxy;

/**
 * @author oky
 * 
 */
public interface HProxy {
	Proxy.Type getProxyType();

	/** 设置是否启用代理 */
	void setEnable(boolean isEnable);

	/** 是否启用代理 */
	boolean isEnable();

	/** 设置代理类型 */
	void setProxyType(Proxy.Type proxyType);

	/** 获取代理地址 */
	String getProxyHostName();

	/** 设置代理地址 */
	void setProxyHostName(String proxyHostName);

	/** 获取代理端口 */
	int getProxyPort();

	/** 设置代理端口 */
	void setProxyPort(int proxyPort);
}
