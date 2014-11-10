/**
 * 
 */
package com.r.web.component.messagecenter.context.htsms;

import org.apache.commons.lang3.StringUtils;

import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpPost;
import com.r.web.component.messagecenter.Message;
import com.r.web.component.messagecenter.MessageParser;
import com.r.web.component.messagecenter.exception.ErrorMessageException;

/**
 * rtx消息<br/>
 * serviceIp : rtx服务器ip地址(必填)<br/>
 * servicePort : rtx服务器端口号(必填,默认"8012")<br/>
 * centontInterceptSize :
 * 内容字符串截取长度,如果发送的内容超过此长度,自动截取信息分第二次发送,再次超过,则再次截取,直到发送完毕,如果此值为0,则不截取长度(默认值为"0")<br/>
 * timeout : 消息发送超时,单位/毫秒,默认10,000毫秒,如果填写此值,则覆盖消息中心顶级的消息超时设置<br/>
 * 
 * @author Administrator
 * 
 */
public class MessageParserHTSMS extends MessageParserHTSMSConfigurator implements MessageParser {
    private static final String SEND_SMS_SIMGLE = "/htWS/Send.aspx"; // 单个发短信
    private static final String SEND_SMS_BATCH = "/htWS/BatchSend.aspx"; // 群发短信
    private static final String ENCODE = "GBK"; // 字符编码

    @Override
    public String getMessageType() {
        return MessageHTSMS.MESSAGE_TYPE;
    }

    @Override
    public Class<?> getMessageModelType() {
        return MessageHTSMSImpl.class;
    }

    @Override
    public void sendMessage(Message message) {
        if (message instanceof MessageHTSMS) {
            MessageHTSMS messageRtx = (MessageHTSMS) message;
            String type = messageRtx.getType(); // 短信类型
            if (StringUtils.isBlank(type)) {
                throw new ErrorMessageException("message's type value is empty!");
            }
            String mobile = messageRtx.getMobile();
            if (StringUtils.isBlank(mobile)) {
                throw new ErrorMessageException("message's mobile value is empty!");
            }
            String content = messageRtx.getContent();
            if (StringUtils.isBlank(content)) {
                throw new ErrorMessageException("message's content value is empty!");
            }
            String sendTime = messageRtx.getSendTime(); // 发送时间(默认为空,立即发送.样例:20141016144300供14位)
            sendTime = StringUtils.isEmpty(sendTime) ? "" : sendTime;

            if (MessageHTSMSImpl.MESSAGE_EVENT_SEND.equals(type)) {// 发送短信
                send(mobile, content, sendTime);
            }

            if (MessageHTSMSImpl.MESSAGE_EVENT_BATCHSEND.equals(type)) {// 群发短信
                batchsend(mobile, content, sendTime);
            }
        }
    }

    /**
     * 发送短信
     * 
     * @param mobile
     *            接收人电话号码
     * @param content
     *            短信内容
     * @param sendTime
     *            发送时间
     */
    private void send(String mobile, String content, String sendTime) {
        logger.debug("MessageParserHTSMS.send();");

        HttpPost post = new HttpPost(ENCODE);
        post.add("CorpID", getCorpID());
        post.add("Pwd", getPwd());
        post.add("Mobile", mobile);
        post.add("Content", content);
        post.add("SendTime", sendTime);
        post.add("Cell", "");

        HttpSocket socket = HttpSocket.newHttpSocket(false, null);
        socket.setTimeout(getTimeout());
        String body = socket.send(getServiceAddr() + SEND_SMS_SIMGLE, post).bodyToString();
        logger.info("send [HTSMS] message : " + body);
    }

    /**
     * 群发发送短信
     * 
     * @param mobile
     *            接收人电话号码
     * @param content
     *            短信内容
     * @param sendTime
     *            发送时间
     */
    private void batchsend(String mobile, String content, String sendTime) {
        logger.debug("MessageParserHTSMS.batchsend();");

        HttpPost post = new HttpPost(ENCODE);
        post.add("CorpID", getCorpID());
        post.add("Pwd", getPwd());
        post.add("Mobile", mobile);
        post.add("Content", content);
        post.add("SendTime", sendTime);
        post.add("Cell", "");

        HttpSocket socket = HttpSocket.newHttpSocket(false, null);
        socket.setTimeout(getTimeout());
        String body = socket.send(getServiceAddr() + SEND_SMS_BATCH, post).bodyToString();
        logger.info("send [HTSMS] message : " + body);
    }
}
