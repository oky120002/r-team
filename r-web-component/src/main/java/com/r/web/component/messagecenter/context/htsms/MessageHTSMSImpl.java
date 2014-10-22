/**
 * 
 */
package com.r.web.component.messagecenter.context.htsms;

import org.apache.commons.lang3.StringUtils;

import com.r.web.component.messagecenter.Message;
import com.r.web.component.messagecenter.exception.ErrorMessageException;

/**
 * HTMSM短信实体<br />
 * 参数1:消息协议类型<br />
 * 参数2:消息类型<br />
 * 参数3:短信接收者手机号码<br />
 * 参数4:短信内容<br />
 * 参数5:短信发送时间(格式为14位长度时间,例如:20141017094053)<br />
 * 
 * @author Administrator
 * 
 */
public class MessageHTSMSImpl implements Message, MessageHTSMS {
    private static final String MESSAGE_PROTOCOL_SPLIT = "/"; // 消息协议分隔符
    public static final String MESSAGE_EVENT_SEND = "send"; // 发送短信(不能用作群发)
    public static final String MESSAGE_EVENT_BATCHSEND = "batchsend"; // 群发短信

    private String type; // 消息类型
    private String mobile; // 短信接收人手机号码
    private String content; // 短信内容
    private String sendTime;// 发送时间(格式为14位长度时间,例如:20141017094053)

    @Override
    public String getMobile() {
        return this.mobile;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public String getSendTime() {
        return this.sendTime;
    }

    @Override
    public String getMessageType() {
        return MessageHTSMS.MESSAGE_TYPE;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setProtocol(String protocol) {
        if (StringUtils.isBlank(protocol)) {
            throw new ErrorMessageException("Protocol is not empty !");
        }

        String[] split = protocol.split(MESSAGE_PROTOCOL_SPLIT);
        // 1:消息协议类型
        if (!getMessageType().equals(split[0])) {
            throw new ErrorMessageException("Agreement is inconsistent with the corresponding protocol parser!");
        }

        // 2:消息类型
        this.type = split[1];

        // 解析单个短信发送
        if (MESSAGE_EVENT_SEND.equals(this.type)) {
            resolveSend(split);
        }

        // 解析群发短信发送
        if (MESSAGE_EVENT_BATCHSEND.equals(this.type)) {
            resolveSend(split);
        }
    }

    @Override
    public void setParameters(Object... objects) {
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    private void resolveSend(String[] split) {
        // 3:短信接收者手机号码
        this.mobile = split[2];

        // 4:短信内容
        this.content = split[3];

        // 5:短信发送时间(格式为14位长度时间,例如:20141017094053)
        try {
            this.sendTime = split[4];
        } catch (Exception e) {
            return;
        }
    }
}
