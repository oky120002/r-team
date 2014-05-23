/**
 * 
 */
package com.r.component.messagecenter.context.rtx;

import com.r.component.messagecenter.exception.MessageCenterException;

/**
 * RTX消息异常
 * 
 * @author oky
 * 
 */
public class MessageRTXException extends MessageCenterException {
	private static final long serialVersionUID = 6799768429927164057L;

	@Override
	protected String doGetErrorCode() {
		return "MESSAGECENTER_RTX_EXCEPTION";
	}

	@Override
	protected String doGetErrorMessage() {
		return "RTX消息异常";
	}

	/** RTX消息异常 */
	public MessageRTXException(String message, Object... objects) {
		super(message, objects);
	}

	/** RTX消息异常 */
	public MessageRTXException(String message, Throwable cause) {
		super(message, cause);
	}

	/** RTX消息异常 */
	public MessageRTXException(String message) {
		super(message);
	}

	public MessageRTXException(String message, int mark) {
		super(message, mark);
	}
}
