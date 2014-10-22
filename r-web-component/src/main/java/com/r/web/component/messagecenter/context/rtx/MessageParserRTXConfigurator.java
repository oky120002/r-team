/**
 * 
 */
package com.r.web.component.messagecenter.context.rtx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体消息体配置器
 * 
 * @author Administrator
 * 
 */
public class MessageParserRTXConfigurator {

    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 链接超时,单位/毫秒<br />
     * 如果此值小于等于0,则无超时(默认等于0)
     */
    protected int timeout = 0;
    /** rtx服务器ip */
    protected String serviceIp;
    /** rtx服务器端口 */
    protected int servicePort = 8012;
    /**
     * 内容字符串截取长度<br />
     * 如果发送的内容超过此长度,自动截取信息分第二次发送,再次超过,则再次截取,直到发送完毕<br />
     * 如果此值为0,则不截取长度
     */
    protected int centontInterceptSize = 0;

    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * 消息发送超时,单位/毫秒,默认10,000毫秒,如果此值小于等于0,则无超时(默认等于0)
     * 
     * @param timeout
     *            the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the serviceIp
     */
    public String getServiceIp() {
        return serviceIp;
    }

    /**
     * 设置rtx服务器IP地址
     * 
     * @param serviceIp
     *            服务器IP地址
     */
    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    /**
     * @return the servicePort
     */
    public int getServicePort() {
        return servicePort;
    }

    /**
     * 设置服务器端口号
     * 
     * @param servicePort
     *            端口号
     */
    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    /**
     * @return the centontInterceptSize
     */
    public int getCentontInterceptSize() {
        return centontInterceptSize;
    }

    /**
     * 内容字符串截取长度<br />
     * 如果发送的内容超过此长度,自动截取信息分第二次发送,再次超过,则再次截取,直到发送完毕<br />
     * 如果此值为0,则不截取长度
     * 
     * @param centontInterceptSize
     *            截取长度
     */
    public void setCentontInterceptSize(int centontInterceptSize) {
        this.centontInterceptSize = centontInterceptSize;
    }

}
