/**
 * 
 */
package com.r.webcomponents.messagecenter;

/**
 * 消息接口<br />
 * 此消息接口不是每个方法都必须实现有效代码<br />
 * 根据需实现自己需要的方法
 * 
 * @author Administrator
 * 
 */
public interface Message {

	/** 获取消息名称 例如 : "rtx" , "qq" , "weixin" , "mail" 等,用户协议中的定义协议类型用 */
	String getMessageType();

	/** 设置消息发送协议字符串(去掉"mc://"后的协议字符串) */
	void setProtocol(String protocol);

	/** 设置消息内容 */
	void setContent(Object... objects);
}
