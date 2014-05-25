/**
 * 
 */
package com.r.core.findproxy.matcher;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.r.core.findproxy.ProxyMatcher;
import com.r.core.httpsocket.HttpSocket;

/**
 * @author Administrator
 *
 */
public class CnProxyMatcher implements ProxyMatcher {
	private static final int TIMEOUT = 3; // 3秒
	private static final String URL = "http://cn-proxy.com"; // 代理页面url
	private static final String PROXY_TEST_URL = "http://www.baidu.com"; // 代理链接测试地址
	private List<Proxy> proxys = new ArrayList<Proxy>();

	@Override
	public void start() {
		String html = getHtml();
		resloveHtml(html);
		checkProxys(TIMEOUT);
	}

	@Override
	public List<Proxy> getProxys() {
		return proxys;
	}

	/** 获取代理页面的html代码 */
	private String getHtml() {
		return HttpSocket.newHttpSocket(false, null).send(URL).bodyToString();
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
		// XXX r-core:findproxy 这里效率问题.需要修改
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
				proxys.add(new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(ip, Integer.valueOf(port).intValue())));
				break;
			}
			index++;
		}

	}

	/**
	 * 检查哪些代理可以使用
	 * 
	 * @param timeout
	 *            超时(秒/单位)
	 */
	private void checkProxys(int timeout) {
		for (Proxy proxy : proxys) {

		}
	}
}
