/**
 * 
 */
package com.r.core.findproxy.context;

import java.net.Proxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.quartz.SchedulerException;

import com.r.core.findproxy.FindProxyMatcher;
import com.r.core.findproxy.FindProxyMatcherListener;
import com.r.core.findproxy.FindProxyUtil;
import com.r.core.findproxy.matcher.CnProxyMatcher;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;

/**
 * @author Administrator
 * 
 */
public class FindProxyContext implements FindProxyMatcherListener {
	private static final Logger logger = LoggerFactory.getLogger(FindProxyContext.class);
	private static final String CHECK_PROXY_URL = "http://www.baidu.com";
	private static final String CHECK_PROXY_CONTAINS = "百度一下";
	private static final int CHECK_PROXY_TIMEOUT = 3_000; // 超时3秒
	private Set<FindProxyMatcher> proxyMatchers = new HashSet<FindProxyMatcher>(); // 所有的代理匹配器
	private Set<Proxy> proxys = Collections.synchronizedSet(new HashSet<Proxy>()); // 所有的代理信息
	private Set<Proxy> proxyFialds = Collections.synchronizedSet(new HashSet<Proxy>()); // 被舍弃的代理

	public FindProxyContext() {
		super();
		logger.debug("FindProxyContext newInstance..........");
	}

	// XXX r-core:findproxy 这里暂时就这样调用.以后根据不同项目再进行更进步封装吧
	/** 初始化Proxy容器 */
	public void init() {
		logger.debug("FindProxyContext init..........");
		addProxyMatcher(new CnProxyMatcher(this));
	}

	/** 执行一次代理匹配 */
	public void execOne() {
		for (FindProxyMatcher matcher : proxyMatchers) {
			matcher.exec();
		}
	}

	/**
	 * 循环执行代理匹配<br/>
	 * 
	 * @param interval
	 *            间隔时间 秒/单位
	 * @throws SchedulerException
	 *             循环执行失败
	 */
	public void execforeach(int interval) throws SchedulerException {
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				for (FindProxyMatcher matcher : proxyMatchers) {
					matcher.exec();
				}
			}
		}, -1, interval, null, null);
	}

	/** 添加代理匹配器 */
	public void addProxyMatcher(FindProxyMatcher matcher) {
		this.proxyMatchers.add(matcher);
	}

	/** 随机获取到一个代理 */
	public Proxy nextProxy() {
		int size = this.proxys.size();
		if (size == 0) {
			return null;
		}
		int target = RandomUtil.randomInteger(size);
		int index = 0;
		for (Proxy proxy : this.proxys) {
			if (index++ == target) {
				return proxy;
			}
		}
		return null;
	}

	/** 随机获取到一个代理,然后把此代理排除代理集 */
	public Proxy nextFialdProxy() {
		int size = this.proxyFialds.size();
		if (size == 0) {
			return null;
		}
		int target = RandomUtil.randomInteger(size);
		int index = 0;
		for (Proxy proxy : this.proxyFialds) {
			if (index++ == target) {
				this.proxyFialds.remove(proxy);
				return proxy;
			}
		}
		return null;
	}

	/** 增加代理 */
	public void addProxy(Proxy proxy) {
		this.proxys.add(proxy);
		this.proxyFialds.add(proxy);
	}

	/** 移出代理 */
	public void removeProxy(Proxy proxy) {
		this.proxys.remove(proxy);
		this.proxyFialds.remove(proxy);
	}

	@Override
	public void matcherAddProxy(final Proxy proxy) {
		TaskUtil.executeTask(new Runnable() {
			@Override
			public void run() {
				if (FindProxyUtil.checkPoxry(proxy, CHECK_PROXY_TIMEOUT, CHECK_PROXY_URL, CHECK_PROXY_CONTAINS)) {
					addProxy(proxy);
				} else {
					removeProxy(proxy);
				}
			}
		});
	}
}
