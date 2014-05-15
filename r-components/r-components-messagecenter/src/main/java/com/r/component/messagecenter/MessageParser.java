/**
 * 
 */
package com.r.component.messagecenter;


/**
 * 消息解析器
 * 
 * @author Administrator
 * 
 */
public interface MessageParser {

	/** 获取消息类型 例如 : "rtx" , "qq" , "weixin" , "mail" 等,用户协议中的定义协议类型用 */
	String getMessageType();

	/**
	 * 发送信息
	 * 
	 * @param message
	 *            需要发送的消息
	 */
	void sendMessage(Message message);

}
