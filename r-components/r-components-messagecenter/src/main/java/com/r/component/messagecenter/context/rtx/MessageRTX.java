/**
 * 
 */
package com.r.component.messagecenter.context.rtx;

/**
 * @author oky
 * 
 */
public interface MessageRTX {

	public static final String MESSAGE_TYPE_NOTIFY = "notify"; // 系统提示信息
	public static final String MESSAGE_TYPE_IM = "im"; // 用户点对点消息

	/** 获取消息标题 */
	String getTitle();

	/** 获取消息类型 */
	String getType();

	/** 获取接收者 */
	String getRecipient();

	/** 获得消息内容 */
	String getContent();

	/** 消息显示时间(毫秒,如果为0则手动关闭) */
	int getDelaytime();

	/** 消息发送者 */
	String getSender();

	/** 消息发送者密码 */
	String getPassword();
}
