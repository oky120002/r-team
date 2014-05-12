package com.r.core.httpsocket.context;

import java.net.Proxy;
import java.net.Proxy.Type;

public class HttpProxy implements HProxy {
	/** 代理类型,如果为null则不设置代理 */
	private Proxy.Type proxyType = null;
	/** 是否启用代理 */
	private boolean isEnable;
	/** 代理主机 */
	private String proxyHostName;
	/** 代理端口 */
	private int proxyPort;

	/** 默认构造函数 */
	private HttpProxy() {
		super();
	}

	/**
	 * 根据传入的代理设置实例化一个HttpProxy
	 * 
	 * @param isEnable
	 *            是否启用
	 * @param proxyType
	 *            代理类型
	 * @param proxyHostName
	 *            代理主机
	 * @param proxyPort
	 *            代理端口
	 * @return
	 */
	public static HttpProxy newInstance(boolean isEnable, Type proxyType, String proxyHostName, int proxyPort) {
		HttpProxy httpProxy = new HttpProxy();
		httpProxy.setEnable(isEnable);
		httpProxy.setProxyType(proxyType);
		httpProxy.setProxyHostName(proxyHostName);
		httpProxy.setProxyPort(proxyPort);
		return httpProxy;
	}

	@Override
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public boolean isEnable() {
		return isEnable;
	}

	/**
	 * @return the proxyType
	 */
	@Override
	public Proxy.Type getProxyType() {
		return proxyType;
	}

	/**
	 * @param proxyType
	 *            the proxyType to set
	 */
	@Override
	public void setProxyType(Proxy.Type proxyType) {
		this.proxyType = proxyType;
	}

	/**
	 * @return the proxyHostName
	 */
	@Override
	public String getProxyHostName() {
		return proxyHostName;
	}

	/**
	 * @param proxyHostName
	 *            the proxyHostName to set
	 */
	@Override
	public void setProxyHostName(String proxyHostName) {
		this.proxyHostName = proxyHostName;
	}

	/**
	 * @return the proxyPort
	 */
	@Override
	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * @param proxyPort
	 *            the proxyPort to set
	 */
	@Override
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + (proxyType == null ? "null" : proxyType.name()) + ":" + proxyHostName + ":" + proxyPort + "]";
	}
}
