/**
 * 
 */
package com.r.web.component.messagecenter.context;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.web.component.messagecenter.MessageInterceptor;
import com.r.web.component.messagecenter.MessageParser;

/**
 * 消息中心基础配置<br/>
 * 
 * @author Administrator
 * 
 */
public class MessageCenterContextConfigurator implements InitializingBean {
    public static final String MESSAGE_PROTOCOL_PREFIX = "mc://"; // 协议头
    public static final String MESSAGE_TYPE_DEFAULT = "default"; // 默认消息类型
    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /** 消息是否异步执行,默认关闭 */
    private boolean asynchronous = true;
    /**
     * 异步执行时最大线程数,默认最大线程为5 ,且不能为负数<br />
     * 如果填写正整数, 则创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。<br />
     * 如果填写非正数(包括0),则创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。<br />
     */
    private int maxThreads = 5;

    /** 消息拦截器 */
    private MessageInterceptor messageInterceptor;

    /** 消息解析器列表 */
    private List<MessageParser> messagesParsers;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("开始进行消息中心的配置..........");
    }

    /**
     * @return the asynchronous
     */
    public boolean isAsynchronous() {
        return asynchronous;
    }

    /**
     * 消息是否异步执行,默认关闭
     * 
     * @param asynchronous
     *            the asynchronous to set
     */
    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

    /**
     * @return the maxThreads
     */
    public int getMaxThreads() {
        return maxThreads;
    }

    /**
     * 异步执行时最大线程数,默认最大线程为5 ,且不能为负数<br />
     * 如果填写正整数, 则创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。<br />
     * 如果填写非正数(包括0),则创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。<br />
     * 
     * @param maxThreads
     *            the maxThreads to set
     */
    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    /**
     * @return the messagesParsers
     */
    public List<MessageParser> getMessagesParsers() {
        return messagesParsers;
    }

    /**
     * 消息解析器列表
     * 
     * @param messagesParsers
     *            the messagesParsers to set
     */
    public void setMessagesParsers(List<MessageParser> messagesParsers) {
        this.messagesParsers = messagesParsers;
    }

    /**
     * @return the messageInterceptor
     */
    public MessageInterceptor getMessageInterceptor() {
        return messageInterceptor;
    }

    /**
     * 消息拦截器
     * 
     * @param messageInterceptor
     *            the messageInterceptor to set
     */
    public void setMessageInterceptor(MessageInterceptor messageInterceptor) {
        this.messageInterceptor = messageInterceptor;
    }

}
