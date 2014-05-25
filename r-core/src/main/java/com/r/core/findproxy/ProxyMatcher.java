/**
 * 
 */
package com.r.core.findproxy;

import java.net.Proxy;
import java.util.List;

/**
 * @author Administrator
 *
 */
public interface ProxyMatcher {

	/** 启动匹配器开始工作 */
	void start();

	/** 获取所有的代理 */
	List<Proxy> getProxys();
}
