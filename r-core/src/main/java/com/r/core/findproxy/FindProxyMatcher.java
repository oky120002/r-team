/**
 * 
 */
package com.r.core.findproxy;

/**
 * @author Administrator
 * 
 */
public interface FindProxyMatcher {

	/** 启动匹配器开始工作一次 */
	FindProxyMatcher exec();

	/** 添加匹配器监听器 */
	FindProxyMatcher addMatcherListener(FindProxyMatcherListener matcherListener);
}
