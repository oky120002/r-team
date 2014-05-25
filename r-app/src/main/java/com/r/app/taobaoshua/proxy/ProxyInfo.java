/**
 * 
 */
package com.r.app.taobaoshua.proxy;

/**
 * 代理信息
 * 
 * @author Administrator
 *
 */
public class ProxyInfo {
	private String name;
	private String host;
	private String port;
	private boolean isUsed;

	public void readLine(String line) {
		String[] split = line.split("|");
		this.name = split[0];
		this.host = split[1];
		this.port = split[2];
		this.name = split[3];
	}

	public void toLine() {
		
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

}
