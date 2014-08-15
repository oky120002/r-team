/**
 * 
 */
package com.r.core.findproxy.matcher;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.apache.commons.lang3.StringUtils;

import com.r.core.findproxy.FindProxyMatcher;
import com.r.core.findproxy.FindProxyMatcherListener;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author Administrator
 * 
 */
public class CnProxyMatcher implements FindProxyMatcher {
	private static final Logger logger = LoggerFactory.getLogger(CnProxyMatcher.class);
	private static final String FIRE_MATCHER_LISTENER_ADDPROXY = "addproxy"; // fire_增加代理
	private static final String PROXY_PAGE_URL = "http://cn-proxy.com"; // 代理页面url
	private static final int PROXY_PAGE_TIMEOUT = 30_000; // 代理页面超时
	private FindProxyMatcherListener matcherListener = null; // 代理匹配器监听器

	public CnProxyMatcher() {
		super();
	}

	public CnProxyMatcher(FindProxyMatcherListener matcherListener) {
		super();
		this.matcherListener = matcherListener;
	}

	@Override
	public FindProxyMatcher exec() {
		logger.debug("开始进行代理获取匹配...........");
		String html = getHtml();
		if (StringUtils.isBlank(html)) {
			return this;
		}
		resloveHtml(html);
		return this;
	}

	@Override
	public FindProxyMatcher addMatcherListener(FindProxyMatcherListener matcherListener) {
		this.matcherListener = matcherListener;
		return this;
	}

	/** 执行监听器 */
	private void fireListener(String fire, Object... objects) {
		if (matcherListener != null && StringUtils.isNotBlank(fire)) {
			switch (fire) {
			case FIRE_MATCHER_LISTENER_ADDPROXY:
				this.matcherListener.matcherAddProxy((Proxy) objects[0]);
				break;
			}
		}

	}

	/** 获取代理页面的html代码 */
	private String getHtml() {
		HttpSocket newHttpSocket = HttpSocket.newHttpSocket(false, null);
		newHttpSocket.setTimeout(PROXY_PAGE_TIMEOUT);
		try {
			return newHttpSocket.send(PROXY_PAGE_URL).bodyToString();
		} catch (Exception e) {
			return null;
		}

	}

	/** 从代理发布页面解析出代理 */
	private void resloveHtml(String html) {
		int curPos = 0;
		while ((curPos = html.indexOf("<td>上次检查</td>")) != -1) {
			html = html.substring(curPos + "<td>上次检查</td>".length());
			String tbody = StringUtils.substringBetween(html, "<tbody>", "</tbody>");
			if (StringUtils.isNotBlank(tbody)) {
				resloveTbody(tbody);
			}
		}
	}

	private void resloveTbody(String tbody) {
		int curPos = 0;
		while ((curPos = tbody.indexOf("<tr>")) != -1) {
			String tr = StringUtils.substringBetween(tbody, "<tr>", "</tr>");
			tbody = tbody.substring(curPos + "<tr>".length());
			if (StringUtils.isNotBlank(tr)) {
				resloveTr(tr);
			}
		}
	}

	private void resloveTr(String tr) {
		int index = 0;
		int curPos = 0;
		String ip = null;
		String port = null;
		while ((curPos = tr.indexOf("<td>")) != -1) {
			String td = StringUtils.substringBetween(tr, "<td>", "</td>");
			tr = tr.substring(curPos + "<td>".length());
			switch (index) {
			case 0:
				ip = td;
				break;
			case 1:
				port = td;
				break;
			case 2:
				Proxy proxy = new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(ip, Integer.valueOf(port).intValue()));
				fireListener(FIRE_MATCHER_LISTENER_ADDPROXY, proxy);
				break;
			}
			index++;
		}

	}
}
