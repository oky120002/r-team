/**
 * 
 */
package com.r.boda.uploadservice.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;

/**
 * 环境参数
 * 
 * @author oky
 * 
 */
public class EnviromentalParameter {
	private String addr; // 本机地址
	private String fileSavePath; // 上传文件保存路径

	/** 获取服务器ip地址 */
	public String getAddr() {
		if (StringUtils.isBlank(addr)) {
			return getLocalHostByIP();
		}
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	/** 获取上传文件保存路径 */
	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}

	/** 获取本机ip地址 */
	private String getLocalHostByIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			return "127.0.0.1";
		}

		return addr.getHostAddress().toString();
	}
}
