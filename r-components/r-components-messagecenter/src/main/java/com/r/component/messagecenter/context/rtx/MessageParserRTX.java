/**
 * 
 */
package com.r.component.messagecenter.context.rtx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import com.r.component.messagecenter.Message;
import com.r.component.messagecenter.MessageParser;
import com.r.component.messagecenter.MessageParserConfigurator;
import com.r.component.messagecenter.context.MessageCenterContextConfigurator;
import com.r.component.messagecenter.exception.ErrorMessageException;
import com.r.core.util.RandomUtil;

/**
 * rtx消息
 * 
 * @author Administrator
 * 
 */
public class MessageParserRTX extends MessageParserConfigurator implements MessageParser {
	private static final String SEND_MESSAGE = "/SendIM.cgi"; // 发送消息的前缀
	private static final String SEND_NOTIFY = "/sendnotify.cgi"; // 发送提示信息前缀

	/** rtx服务器ip */
	private String serviceIp;
	/** rtx服务器端口 */
	private int servicePort;
	/**
	 * 内容字符串截取长度<br />
	 * 如果发送的内容超过此长度,自动截取信息分第二次发送,再次超过,则再次截取,直到发送完毕<br />
	 * 如果此值为0,则不截取长度
	 */
	private int centontInterceptSize;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
	}

	@Override
	public String getMessageType() {
		return MessageCenterContextConfigurator.MESSAGE_TYPE_RTX;
	}

	@Override
	public void sendMessage(Message message) {

		if (message instanceof MessageRTX) {
			MessageRTX messageRtx = (MessageRTX) message;
			String recipient = messageRtx.getRecipient();
			if (recipient == null || recipient.trim().length() == 0) {
				throw new ErrorMessageException("message's recipient value is empty!");
			}
			String title = messageRtx.getTitle();
			String content = messageRtx.getContent();
			if (content == null || content.trim().length() == 0) {
				throw new ErrorMessageException("message's content value is empty!");
			}
			int delaytime = messageRtx.getDelaytime(); // 显示时间(毫秒,如果为0则手动关闭)

			if (centontInterceptSize == 0) {
				sendNotify(recipient, title, content, delaytime);
				return;
			}

			// 截取数据
			String id = RandomUtil.randomString(6);
			int sumpage = content.length() / centontInterceptSize + 1;
			int index = 1;
			String substring;
			while (content.length() > centontInterceptSize) {
				substring = id + "(" + index++ + "/" + sumpage + ")\r\n" + content.substring(0, centontInterceptSize);
				sendNotify(recipient, title, substring, delaytime);
				content = content.substring(centontInterceptSize);
			}
			substring = id + "(" + index++ + "/" + sumpage + ")\r\n" + content;
			sendNotify(recipient, title, substring, delaytime);
		} else {
			throw new ErrorMessageException("MessageBean is not instanceof MessageRTX : [MessageBean's type is " + message.getMessageType() + "]");
		}

	}

	/**
	 * 发送通知到RTX指定接收人
	 * 
	 * @param receivers
	 *            接收人RTX登录名，多人用分号“,”隔开。
	 * @param title
	 *            通知标题
	 * @param msg
	 *            通知内容
	 * @param delaytime
	 *            显示时间(毫秒,如果为0则手动关闭)
	 */
	private void sendNotify(String receivers, String title, String msg, int delaytime) {
		logger.debug("MessageParserRTX.sendNotify();");
		BufferedReader in = null;
		HttpURLConnection urlConnection = null;
		try {
			StringBuilder sendMsgParams = new StringBuilder(SEND_NOTIFY);
			sendMsgParams.append("?msg=" + URLEncoder.encode(msg, "gbk")).append("&receiver=").append(receivers).append("&title=").append(URLEncoder.encode(title, "gbk")).append("&delaytime=").append(delaytime);
			URL url = new URL("HTTP", serviceIp, servicePort, sendMsgParams.toString());
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(timeout);
			in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "gbk"));
			StringBuilder sb = new StringBuilder();
			String content;
			while ((content = in.readLine()) != null) {
				sb.append(content);
			}
			content = sb.toString();
			logger.info("send [RTX] message : " + content);

			if (!content.contains("操作成功")) {
				throw new ErrorMessageException("RTX message sending failed!");
			}
		} catch (IOException e) {
			throw new ErrorMessageException("Connection RTX server fails!", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e2) {
				}
			}
			if (urlConnection != null) {
				try {
					urlConnection.disconnect();
				} catch (Exception e2) {
				}
			}
		}
	}

	/**
	 * 发送消息到RTX指定人员上
	 * 
	 * @param sender
	 *            发送人RTX登录名
	 * @param password
	 *            发送人RTX登录密码
	 * @param receivers
	 *            接收人RTX登录名，多人用分号“,”隔开。
	 * @param msg
	 *            消息内容 ,如需要发隐式链接,格式如: "[腾讯|http://www.qq.com ]"(最后一个空格不能少)
	 */
	@SuppressWarnings("unused")
	private void sendIm(String sender, String password, String receivers, String msg) {
		logger.debug("MessageParserRTX.sendIm();");
		try {
			StringBuffer sendMsgParams = new StringBuffer(SEND_MESSAGE);
			sendMsgParams.append("?sender=").append(sender).append("&pwd=&receivers=").append(receivers).append("&msg=" + URLEncoder.encode(msg, "gbk")).append("&sessionid={").append(randomUUID()).append("}");
			URL url = new URL("HTTP", serviceIp, servicePort, sendMsgParams.toString());
			HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				logger.info("======sendMsg========" + line);
			}
			in.close();
			httpconn.disconnect();
		} catch (Exception e) {
			throw new ErrorMessageException("Connection RTX server fails!", e);
		}
	}

	/**
	 * @return the serviceIp
	 */
	public String getServiceIp() {
		return serviceIp;
	}

	/**
	 * @param serviceIp
	 *            the serviceIp to set
	 */
	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	/**
	 * @return the servicePort
	 */
	public int getServicePort() {
		return servicePort;
	}

	/**
	 * @param servicePort
	 *            the servicePort to set
	 */
	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}

	/**
	 * @return the centontInterceptSize
	 */
	public int getCentontInterceptSize() {
		return centontInterceptSize;
	}

	/**
	 * @param centontInterceptSize
	 *            the centontInterceptSize to set
	 */
	public void setCentontInterceptSize(int centontInterceptSize) {
		this.centontInterceptSize = centontInterceptSize;
	}

	/** 生成随机seeeionid */
	private String randomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
