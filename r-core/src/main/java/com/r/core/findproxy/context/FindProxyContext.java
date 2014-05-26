/**
 * 
 */
package com.r.core.findproxy.context;

import java.net.Proxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.quartz.SchedulerException;

import com.r.core.findproxy.ProxyMatcher;
import com.r.core.findproxy.ProxyMatcherListener;
import com.r.core.findproxy.matcher.CnProxyMatcher;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.httpsocket.context.responseheader.ResponseStatus;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;

/**
 * @author Administrator
 * 
 */
public class FindProxyContext implements ProxyMatcherListener {
	private static final Logger logger = LoggerFactory.getLogger(FindProxyContext.class);
	private static final String CHECK_PROXY_URL = "http://www.baidu.com";
	private static final int TIMEOUT = 3_000; // 超时3秒
	private Set<ProxyMatcher> proxyMatchers = new HashSet<ProxyMatcher>(); // 所有的代理匹配器
	private Set<Proxy> proxys = Collections.synchronizedSet(new HashSet<Proxy>()); // 所有的代理信息
	private Set<Proxy> proxyFialds = Collections.synchronizedSet(new HashSet<Proxy>()); // 被舍弃的代理

	// XXX r-core:findproxy 这里暂时就这样调用.以后根据不同项目再进行更进步封装吧
	/** 初始化Proxy容器 */
	public void init() {
		addProxyMatcher(new CnProxyMatcher(this));
	}

	/** 执行一次代理匹配 */
	public void execOne() {
		for (ProxyMatcher matcher : proxyMatchers) {
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
				for (ProxyMatcher matcher : proxyMatchers) {
					matcher.exec();
				}
			}
		}, -1, interval, null, null);
	}

	/** 添加代理匹配器 */
	public void addProxyMatcher(ProxyMatcher matcher) {
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
				if (checkPoxry(proxy)) {
					addProxy(proxy);
				} else {
					removeProxy(proxy);
				}
			}
		});
	}

	/** 检查代理是否可以使用 */
	private boolean checkPoxry(Proxy proxy) {
		if (proxy == null) {
			return false;
		}
		long startTime = 0;
		long endTime = 0;
		try {
			HttpProxy httpProxy = HttpProxy.newInstance(true, proxy);
			HttpSocket httpSocket = HttpSocket.newHttpSocket(true, httpProxy);
			startTime = System.currentTimeMillis();
			ResponseHeader send = httpSocket.send(CHECK_PROXY_URL);
			endTime = System.currentTimeMillis();

			if (!ResponseStatus.s200.equals(send.getStatus())) {
				throw new SecurityException("非正常返回");
			}

			if (endTime - startTime <= TIMEOUT) {
				String bodyToString = send.bodyToString();
				if (bodyToString.contains("百度一下")) {
					logger.debug("代理 {} 链接成功!", proxy);
					return true;
				}
			} else {
				logger.warn("代理 {} 连接超时[{}毫秒]。", proxy.toString(), TIMEOUT);
			}
		} catch (Exception e) {
			logger.warn("代理 {} 连接测试失败 : " + e.getMessage(), proxy.toString());
		}
		return false;
	}
}
