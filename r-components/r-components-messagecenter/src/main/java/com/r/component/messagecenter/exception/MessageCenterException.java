/**
 * 
 */
package com.r.component.messagecenter.exception;

import com.r.core.exceptions.SException;

/**
 * 消息中心异常
 * 
 * @author oky
 * 
 */
public class MessageCenterException extends SException {
	private static final long serialVersionUID = -3813147688555779719L;

	@Override
	protected String doGetErrorCode() {
		return "MESSAGE_CENTER_EXCEPTION";
	}

	@Override
	protected String doGetErrorMessage() {
		return "消息中心异常";
	}

	/** 消息中心错误 */
	public MessageCenterException(String message, Object... objects) {
		super(message, objects);
	}

	/** 消息中心错误 */
	public MessageCenterException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 消息中心错误 */
	public MessageCenterException(String message) {
		super(message);
	}

	public MessageCenterException(String message, int mark) {
		super(message, mark);
	}

}
