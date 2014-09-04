/**
 * 
 */
package com.r.webcomponents.messagecenter.context.rtx;

import com.r.webcomponents.messagecenter.Message;
import com.r.webcomponents.messagecenter.MessageDefaultImpl;
import com.r.webcomponents.messagecenter.context.MessageCenterContextConfigurator;
import com.r.webcomponents.messagecenter.exception.ErrorMessageException;

/**
 * rtx消息实体
 * 
 * @author Administrator
 * 
 */
public class MessageRTXImpl extends MessageDefaultImpl implements Message, MessageRTX {

	/** 消息标题 */
	private String title;
	/** 获取消息类型 */
	private String type;
	/** 消息接收人 */
	private String recipient;
	/** 消息内容 */
	private String content;
	/** 消息展示时间 */
	private int delaytime;

	/** 发送者 */
	private String sender;
	/** 发送者密码 */
	private String password;

	@Override
	public String getMessageType() {
		return MessageCenterContextConfigurator.MESSAGE_TYPE_RTX;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getType() {
		return type;
	}

	/** 获取接收者,用";"分割 */
	@Override
	public String getRecipient() {
		return this.recipient;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public int getDelaytime() {
		return this.delaytime;
	}

	@Override
	public String getSender() {
		return this.sender;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	protected void resolveProtocol() {
		super.resolveProtocol();
		// 解析消息协议
		{
			String[] split = super.protocol.split("/");
			// 1:消息协议类型
			if (!getMessageType().equals(split[0])) {
				throw new ErrorMessageException("Agreement is inconsistent with the corresponding protocol parser!");
			}

			// 2:消息类型
			this.type = split[1];

			if (MessageRTX.MESSAGE_TYPE_NOTIFY.equals(this.type)) {
				resolveNotify(split);
			}

			if (MessageRTX.MESSAGE_TYPE_IM.equals(this.type)) {
				resolveNotify(split);
			}

		}

		// 解析额外参数
		{
			if (super.objects != null && super.objects.length != 0) {
				// 1:消息发送者
				this.sender = String.valueOf(objects[0]);
			}

		}
	}

	/**
	 * 
	 * @param split
	 */
	private void resolveNotify(String[] split) {
		// 3:消息接收者
		this.recipient = split[2];

		// 4:消息标题
		this.title = split[3];

		// 5:消息显示时间,如果为0则手动关闭
		try {
			this.delaytime = Integer.valueOf(split[4]);
		} catch (Exception e) {
			throw new ErrorMessageException("The second parameter is a positive integer!");
		}

		StringBuilder sb = new StringBuilder();
		int size = split.length;
		for (int index = 5; index < size; index++) {
			sb.append(split[index]).append('/');
		}

		// 6:消息内容
		this.content = sb.toString().substring(0, sb.length() - 1);
		System.out.println(sb.length() + "  :  " + this.content);
	}

	@SuppressWarnings("unused")
	private void resolveIM() {
		// TODO r-components-messagecenter 完成IM消息解析和发送
	}
}
