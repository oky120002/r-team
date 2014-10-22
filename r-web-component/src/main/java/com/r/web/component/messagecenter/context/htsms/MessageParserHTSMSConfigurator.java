/**
 * 
 */
package com.r.web.component.messagecenter.context.htsms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体消息体配置器
 * 
 * @author Administrator
 * 
 */
public class MessageParserHTSMSConfigurator {

    private static final String SERVICE_ADDR = "www.ht3g.com";
    private static final int SERVICE_PORT = 80;

    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /** 企业发送账号 */
    protected String corpID;
    /** 企业发送账号的密码 */
    protected String pwd;
    /** 短信发送超时时间 */
    protected int timeout = 5 * 1000;

    public String getCorpID() {
        return corpID;
    }

    /** 企业发送账号 */
    public void setCorpID(String corpID) {
        this.corpID = corpID;
    }

    public String getPwd() {
        return pwd;
    }

    /** 短信发送超时时间 */
    public int getTimeout() {
        return timeout;
    }

    /**短信发送超时时间*/
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /** 企业发送账号的密码 */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /** 短信接口地址 */
    public String getServiceAddr() {
        return SERVICE_ADDR;
    }

    /** 短信接口端口 */
    public int getServicePort() {
        return SERVICE_PORT;
    }
}
