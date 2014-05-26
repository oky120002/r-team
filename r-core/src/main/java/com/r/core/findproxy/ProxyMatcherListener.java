/**
 * 
 */
package com.r.core.findproxy;

import java.net.Proxy;

/**
 * 匹配器监听器
 * 
 * @author oky
 * 
 */
public interface ProxyMatcherListener {
	/**
	 * 匹配器实时增加代理<br />
	 * 
	 * @param proxy
	 *            此实例不能使用new关键字进行创建.
	 */
	void matcherAddProxy(Proxy proxy);
}
