/**
 * 
 */
package com.r.web.component.messagecenter.context.htsms;

/**
 * 化唐短信接口
 * 
 * @author oky
 * 
 */
public interface MessageHTSMS {
    public static final String MESSAGE_TYPE = "htsms"; // 消息类型-短信

    /** 获取发送类型 */
    String getType();

    /** 获取接收方电话号码 */
    String getMobile();

    /** 获得消息内容 */
    String getContent();

    /** 发送时间(默认为空,立即发送.样例:20141016144300 供14位) */
    String getSendTime();
}
