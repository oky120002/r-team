/**
 * 描          述:  描述
 * 修  改   人:  oky
 * 修改时间:  2014-1-6
 * 修改描述:
 */
package com.r.core.httpsocket.context;

import com.r.core.util.AssertUtil;

/**
 * url
 * 
 * @author oky
 * @version 版本号, 2014-1-6
 */
public class HttpUrl {
    /** 主机 */
    private String host;
    /** 地址 */
    private String url;
    /** 端口 */
    private int port = 80;

    /** 默认构造函数 */
    private HttpUrl() {
        super();
    }

    /** 默认构造函数 */
    private HttpUrl(String httpUrl) {
        super();
        AssertUtil.isNotBlank("不能传入空的httpUrl", httpUrl);
        detachHttpUrl(httpUrl);
    }

    /** 根据传入的URL实例化一个HttpUrl */
    public static HttpUrl newInstance(String url) {
        return new HttpUrl(url);
    }

    /** 根据传入的URL实例化一个HttpUrl */
    public static HttpUrl newInstance(String url, int port) {
        HttpUrl hu = new HttpUrl(url);
        hu.port = port;
        return hu;
    }

    /** 解析httpurl */
    private void detachHttpUrl(String httpUrl) {
        String tempHttpUrl = httpUrl.trim();
        if (tempHttpUrl.toUpperCase().startsWith("HTTPS://")) {
            // tempHttpUrl = tempHttpUrl.substring(8);
            throw new NullPointerException("暂时不支持https协议");
        }

        if (tempHttpUrl.toUpperCase().startsWith("HTTP://")) {
            tempHttpUrl = tempHttpUrl.substring(7);
        }

        int token = tempHttpUrl.indexOf("/");
        String host = tempHttpUrl;
        String url = "/";
        if (token > 0) {
            host = tempHttpUrl.substring(0, token);
            url = tempHttpUrl.substring(token);
        }

        token = host.indexOf(":");
        String port = "80";
        if (token > 0) {
            port = host.substring(token + 1, host.length());
        }

        this.host = host;
        this.url = url;
        this.port = Integer.valueOf(port).intValue();
    }

    /**
     * @return 返回 host
     */
    public String getHost() {
        if (port == 80) {
            return host;
        } else {
            return host + ":" + port;
        }
    }

    /**
     * @return 返回 url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return 返回 port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

}
