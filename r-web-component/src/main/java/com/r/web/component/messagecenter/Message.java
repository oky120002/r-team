/**
 * 
 */
package com.r.web.component.messagecenter;

/**
 * 消息接口<br />
 * 根据消息类型实现实现方法
 * 
 * @author Administrator
 * 
 */
public interface Message {

    /** 获取消息名称 例如 : "rtx" , "qq" , "weixin" , "mail" 等,用户协议中的定义协议类型用 */
    String getMessageType();

    /** 设置消息发送协议字符串 */
    void setProtocol(String protocol);

    /** 设置消息额外参数 */
    void setParameters(Object... objects);
}
