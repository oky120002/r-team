/**
 * 
 */
package com.r.web.component.messagecenter.context.rtx;

import com.r.web.component.messagecenter.Message;
import com.r.web.component.messagecenter.exception.ErrorMessageException;
import com.r.web.component.messagecenter.exception.ErrorProtocolException;

/**
 * rtx消息实体
 * 
 * @author Administrator
 * 
 */
public class MessageRTXImpl implements Message, MessageRTX {
    private static final String MESSAGE_PROTOCOL_SPLIT = "/"; // 消息协议分隔符
    public static final String MESSAGE_EVENT_NOTIFY = "notify"; // 系统提示信息
    public static final String MESSAGE_EVENT_IM = "im"; // 用户点对点消息

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
        return MessageRTX.MESSAGE_TYPE;
    }

    @Override
    public void setProtocol(String protocol) {
        if (protocol == null || protocol.trim().length() == 0) {
            throw new ErrorMessageException("Protocol is not empty !");
        }

        String[] split = protocol.split(MESSAGE_PROTOCOL_SPLIT);
        // 1:消息协议类型
        if (!getMessageType().equals(split[0])) {
            throw new ErrorMessageException("Agreement is inconsistent with the corresponding protocol parser!");
        }

        // 2:消息类型
        this.type = split[1];

        if (MESSAGE_EVENT_NOTIFY.equals(this.type)) {
            resolveNotify(split);
        }

        if (MESSAGE_EVENT_IM.equals(this.type)) {
            resolveIM(split);
        }
    }

    /**
     * 设置消息发送者(可以为null)
     * 
     * @param parameters
     *            <li>parameters[0] 消息发送者</li>
     */
    @Override
    public void setParameters(Object... parameters) {
        if (parameters != null && parameters.length != 0) {
            // 1:消息发送者
            this.sender = String.valueOf(parameters[0]);
        }
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

    /**
     * 解析系统消息内容
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
    }

    /**
     * 解析点对点IM消息内容
     */
    private void resolveIM(String[] split) {
        throw new ErrorProtocolException("IM点对点消息发送失败!RTX点对点IM消息发送机制还没有实现,请联系管理员");
    }
}
