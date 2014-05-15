/**
 * 
 */
package com.r.component.messagecenter;

import com.r.component.messagecenter.context.MessageCenterContextConfigurator;

/**
 * 消息默认实现
 * 
 * @author Administrator
 * 
 */
public class MessageDefaultImpl implements Message {

	/** 消息协议原始字符串 */
	protected String protocol;
	/** 消息内容 */
	protected Object[] objects;

	public String getMessageType() {
		return MessageCenterContextConfigurator.MESSAGE_TYPE_DEFAULT;
	}

	@Override
	public void setProtocol(String protocol) {
		this.protocol = protocol;
		resolveProtocol();
	}

	@Override
	public void setContent(Object... objects) {
		this.objects = objects;
	}

	/** 解析消息协议 */
	protected void resolveProtocol() {
		// 可以覆盖此方法来实现设置协议时自动解析,也可以不理会此方法自己触发解析协议动作
	}
}
