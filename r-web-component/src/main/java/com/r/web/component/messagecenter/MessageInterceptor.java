/**
 * 
 */
package com.r.web.component.messagecenter;

import com.r.web.component.messagecenter.exception.MessageCenterException;

/**
 * 消息拦截器
 * 
 * @author oky
 * 
 */
public interface MessageInterceptor {

    /**
     * 消息发送前
     * 
     * @param message
     *            消息
     * @return true:继续发送消息|false:中断消息的发送
     */
    boolean beforeMessageSend(Message message);

    /**
     * 消息发送后
     * 
     * @param message
     *            消息Bean
     * @param e
     *            错误信息,如果消息发送没有错误则传入null
     * @return true:异常已经处理,不会抛出异常|false:异常没有处理,会在发送消息后抛出异常
     */
    boolean afterMessageSend(Message message, MessageCenterException e);
}
