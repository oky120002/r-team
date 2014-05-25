/**
 * 
 */
package com.r.core.findproxy.context;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.r.core.findproxy.ProxyMatcher;
import com.r.core.util.RandomUtil;

/**
 * @author Administrator
 *
 */
public class FindProxyContext {

	private Set<ProxyMatcher> proxyMatchers = new HashSet<ProxyMatcher>(); // 所有的代理匹配器

	private List<Proxy> proxys = Collections.synchronizedList(new ArrayList<Proxy>()); // 所有的代理信息
	private List<Proxy> proxyFialds = Collections.synchronizedList(new ArrayList<Proxy>()); // 被舍弃的代理

	/** 初始化Proxy容器 */
	public void init() {
		if (CollectionUtils.isNotEmpty(this.proxyMatchers)) {
			for (ProxyMatcher proxyMatcher : this.proxyMatchers) {
				List<Proxy> allMatcherProxys = proxyMatcher.getProxys();
				this.proxys.addAll(allMatcherProxys);
				this.proxyFialds.addAll(allMatcherProxys);
			}
		} else {
			
		}
	}

	/** 添加代理匹配器 */
	public void addProxyMatcher(ProxyMatcher matcher) {
		this.proxyMatchers.add(matcher);
	}

	/** 随机获取到一个代理 */
	public Proxy getRandomProxy() {
		return this.proxys.get(RandomUtil.randomInteger(proxyMatchers.size()));
	}

	/** 随机获取到一个代理,然后把此代理排除下次获得 */
	public Proxy getRaindomFialdProxy() {
		return this.proxyFialds.remove(RandomUtil.randomInteger(proxyMatchers.size()));
	}
}
