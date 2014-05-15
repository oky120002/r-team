/**
 * 
 */
package com.r.component.messagecenter.exception;

/**
 * 消息Bean异常
 * 
 * @author oky
 * 
 */
public class ErrorMessageException extends MessageCenterException {
	private static final long serialVersionUID = 1420223713887179414L;

	@Override
	protected String doGetErrorCode() {
		return "MESSAGECENTER_MESSAGE_BEAN_ERROR";
	}

	@Override
	protected String doGetErrorMessage() {
		return "消息Bean异常";
	}

	/** 消息Bean异常 */
	public ErrorMessageException(String message, Object... objects) {
		super(message, objects);
	}

	/** 消息Bean异常 */
	public ErrorMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	/** 消息Bean异常 */
	public ErrorMessageException(String message) {
		super(message);
	}
}
