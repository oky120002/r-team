package com.r.core.httpsocket.context.responseheader;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import com.r.core.exceptions.arg.ArgIllegalException;
import com.r.core.util.AssertUtil;

/**
 * 返回时的状态
 * 
 * @author Rain
 * 
 */
public enum ResponseStatus {
	s200("200", "OK", "客户端请求成功，正常返回"), // 200
	s304("304", "Not Modified", "没有修改,缓存中"), // 304
	s302("302", "Moved Temporatily", "被请求的资源暂时转移"), // 302
	s400("400", "Bad Request", "客户端请求有语法错误，不能被服务器所理解"), // 400
	s401("401", "Unauthorized", "请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用 "), // 401
	s403("403", "Forbidden", "服务器收到请求，但是拒绝提供服务"), // 403
	s404("404", "Not Found", "没有找到资源"), // 404
	s500("500", "Internal Server Error", "服务器发生不可预期的错误"), // 500
	s502("502", "Bad Gateway", "服务器作为网关或代理，从上游服务器收到无效响应。 "), // 502
	s503("503", "Server Unavailable", "服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。"), // 503
	s504("504", "Gateway Timeout", "服务器作为网关或代理，但是没有及时从上游服务器收到请求。"), // 504
	;

	/** 状态编码 */
	private String code;
	/** 状态标题 */
	private String title;
	/** 状态说明 */
	private String caption;

	public String getCode() {
		return this.code;
	}

	public String getTitle() {
		return this.title;
	}

	public String getCaption() {
		return this.caption;
	}

	ResponseStatus(String code, String title, String caption) {
		this.code = code;
		this.title = title;
		this.caption = caption;
	}

	/** 根据状态字符串返回状态,如果找不到则返回空 */
	public static ResponseStatus getResponseStatus(String status) {
		AssertUtil.isNotBlank("不能传入空的返回状态", status);
		for (ResponseStatus rs : values()) {
			if (rs.getCode().equals(status)) {
				return rs;
			}
		}
		throw new ArgIllegalException("传入了未知的返回状态:[" + status + "]");
	}

	@Override
	public String toString() {
		return MessageFormatter.arrayFormat(" {} - {}({})", new String[] { this.code, this.title, StringUtils.abbreviate(this.caption, 10) }).getMessage();
	}
}
