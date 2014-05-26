/**
 * 
 */
package com.r.core.findproxy;

/**
 * @author Administrator
 * 
 */
public interface ProxyMatcher {

	/** 启动匹配器开始工作一次 */
	ProxyMatcher exec();

	/** 添加匹配器监听器 */
	ProxyMatcher addMatcherListener(ProxyMatcherListener matcherListener);
}
