package com.r.core.httpsocket;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.context.Cookie;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.httpsocket.context.HttpUrl;
import com.r.core.httpsocket.context.RequestHeader;
import com.r.core.httpsocket.context.ResponseHeader;

/**
 * 提供对Http协议的发送和接收<br />
 * 只支持http代理模式
 * 
 * @author Rain
 * 
 */
public class HttpSocket implements Serializable {
	private static final long serialVersionUID = -9216646912368723831L;

	/** http发送头信息 */
	private RequestHeader requestHeader = null;
	/** 连接超时时间,默认5秒 */
	private int timeout = 5 * 1000; //
	/** 链接是否空闲中 */
	private boolean isConnectionFree = true;

	/** cookies */
	private Map<String, Cookie> cookies = new HashMap<String, Cookie>();
	/** 是否持有返回的cookies,如果持有,则每次返回时自动获取,发送时自动发送 */
	private boolean isHoldCookies = true;

	private HttpSocket(boolean isHoldCookies) {
		this.isHoldCookies = isHoldCookies;
		this.requestHeader = RequestHeader.newRequestHeaderByEmpty();
	}

	// **-----------about new HttpSocket functions-----------**//

	/**
	 * 根据发送头信息创建HttpSocket
	 * 
	 * @param holdCookies
	 *            是否保持cookies
	 */
	public static HttpSocket newHttpSocket(boolean holdCookies, HttpProxy httpProxy) {
		return new HttpSocket(holdCookies).setProxy(httpProxy);
	}

	// **-----------about send functions-----------**//

	/**
	 * 发送请求
	 * 
	 * @param httpUrl
	 * @return
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 */
	public ResponseHeader send(String httpUrl) throws NetworkIOReadErrorException {
		return send(RequestHeader.newRequestHeaderByGet(HttpUrl.newInstance(httpUrl), requestHeader.getHttpProxy()));
	}

	/**
	 * 发送请求
	 * 
	 * @param httpUrl
	 * @param post
	 * @param encoding
	 *            post的转换编码,如果为空,则不进行任何的编码转换
	 * @return
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 */
	public ResponseHeader send(String httpUrl, String post, String encoding) throws NetworkIOReadErrorException {
		return send(RequestHeader.newRequestHeaderByPost(HttpUrl.newInstance(httpUrl), post, encoding, requestHeader.getHttpProxy()));
	}

	/**
	 * 发送请求
	 * 
	 * @param httpUrl
	 * @param cookies
	 * @param headers
	 * @return
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 */
	public ResponseHeader send(String httpUrl, Map<String, Cookie> cookies, Map<String, String> headers) throws NetworkIOReadErrorException {
		return send(RequestHeader.newRequestHeaderByGet(HttpUrl.newInstance(httpUrl), requestHeader.getHttpProxy()).putCookies(cookies).putAllHeader(headers));
	}

	/**
	 * 发送请求
	 * 
	 * @param httpUrl
	 * @param post
	 * @param encoding
	 * @param cookies
	 * @param headers
	 * @return
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 */
	public ResponseHeader send(String httpUrl, String post, String encoding, Map<String, Cookie> cookies, Map<String, String> headers) throws NetworkIOReadErrorException {
		return send(RequestHeader.newRequestHeaderByPost(HttpUrl.newInstance(httpUrl), post, encoding, requestHeader.getHttpProxy()).putCookies(cookies).putAllHeader(headers));
	}

	/**
	 * 上传文件<br />
	 * 部分实现 RFC1867协议
	 * 
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 * @throws IOException
	 *             文件IO读取错误
	 */
	public ResponseHeader send(String httpUrl, File file, Map<String, String> pars, String parName, String fileName) throws NetworkIOReadErrorException, IOException {
		return send(RequestHeader.newRequestHeaderByUpFile(HttpUrl.newInstance(httpUrl), file, requestHeader.getHttpProxy(), pars, parName, fileName));
	}

	/**
	 * 发送请求<br />
	 * 如果调用地方法来发送请求,请自己处理代理设置
	 * 
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 */
	public synchronized ResponseHeader send(RequestHeader requestHeader) throws NetworkIOReadErrorException {
		this.requestHeader = requestHeader;
		return send();
	}

	/**
	 * 发送请求(此方法是阻塞式方法)
	 * 
	 * @return 返回报文头
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 */
	public synchronized ResponseHeader send() throws NetworkIOReadErrorException {
		this.isConnectionFree = false;
		ResponseHeader responseHeader = ResponseHeader.newResponseHeaderByEmpty();
		Socket socket = null;
		InputStream inputStream = null;
		try {
			// 开始发送数据
			// 如果保持cookies,则添加保持着的cookies
			if (this.isHoldCookies) {
				this.requestHeader.putCookies(this.cookies);
			}

			// 发送命令,同时返回"返回报文"的流通道
			socket = this.requestHeader.send(this.timeout);

			// 解析返回的报文
			inputStream = socket.getInputStream();
			responseHeader.resolveResponse(inputStream);

			// 如果保持cookies,则从返回头中提取出cookies保存起来
			if (this.isHoldCookies) {
				this.cookies.putAll(responseHeader.getCookies());
			}
		} catch (SocketTimeoutException ste) {
			throw new NetworkIOReadErrorException("获取数据超时!", ste);
		} catch (IOException e) {
			throw new NetworkIOReadErrorException("网络错误!", e);
		} finally {
			// 已经发送完成
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(socket);
			this.isConnectionFree = true;
		}
		return responseHeader;
	}

	// **-----------setter and getter-----------**//

	/** 获得发送RequestHeader的头信息 */
	public RequestHeader getRequestHeader() {
		return this.requestHeader;
	}

	/**
	 * 设置RequestHeader的头信息<br />
	 * 如果调用地方法来发送请求,请自己处理代理设置
	 */
	public HttpSocket setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
		return this;
	}

	/**
	 * @return 返回 是否保持cookies
	 */
	public boolean isHoldCookies() {
		return isHoldCookies;
	}

	/**
	 * @param 对holdCookies进行赋值
	 */
	public HttpSocket setHoldCookies(boolean holdCookies) {
		this.isHoldCookies = holdCookies;
		return this;
	}

	/** 获取代理设置 */
	public HttpProxy getProxy() {
		return requestHeader.getHttpProxy();
	}

	/** 设置代理 */
	public HttpSocket setProxy(HttpProxy httpProxy) {
		requestHeader.setHttpProxy(httpProxy);
		return this;
	}

	/** 清空代理 */
	public HttpSocket clearProxy() {
		requestHeader.clearProxy();
		return this;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * 默认5*1000毫秒
	 * 
	 * @param timeout
	 *            the timeout to set(单位 毫秒)
	 */
	public HttpSocket setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * @return the cookies
	 */
	public Map<String, Cookie> getCookies() {
		return cookies;
	}

	/**
	 * @param cookies
	 *            the cookies to set
	 */
	public HttpSocket setCookies(Map<String, Cookie> cookies) {
		this.cookies = cookies;
		return this;
	}

	/** 获取指定的Cookie */
	public Cookie getCookie(String key) {
		return this.cookies.get(key);
	}

	/** 链接是否空闲 */
	public boolean isConnectionFree() {
		return isConnectionFree;
	}

	public static void main(String[] args) throws IOException {
		HttpSocket hs = null;

		// System.out.println("-------------保存返回的图片-----------------");
		// hs = HttpSocket.newHttpSocket(false, null);
		// hs.send("http://su.bdimg.com/static/superpage/img/logo_white.png");
		// System.out.println(hs.getResponseHeader().getResponse());
		// ImageUtil.saveImageToFile(hs.getResponseBodyToImage(), new
		// File("d://baidu." +
		// hs.getResponseHeader().getContentType().getResponseContentTypeCode().getFileSuffixName()));

		System.out.println("-------------读取返回的字符串-----------------");
		hs = HttpSocket.newHttpSocket(true, null);
		ResponseHeader responseHeader = hs.send("http://www.baidu.com/");
		System.out.println(responseHeader.getResponse());
		System.out.println(responseHeader.bodyToFile("d://baidu.txt"));
	}
}